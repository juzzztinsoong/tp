---
layout: page
title: Developer Guide
---

- Table of Contents
  {:toc}

---

## **Acknowledgements**

- {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

---

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

---

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.

</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The **_Architecture Diagram_** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.

- At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
- At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

- [**`UI`**](#ui-component): The UI of the App.
- [**`Logic`**](#logic-component): The command executor.
- [**`Model`**](#model-component): Holds the data of the App in memory.
- [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The _Sequence Diagram_ below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

- defines its _API_ in an `interface` with the same name as the Component.
- implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

- executes user commands using the `Logic` component.
- listens for changes to `Model` data so that the UI can be updated with the modified data.
- keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
- depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:

- When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
- All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component

**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />

The `Model` component,

- stores the address book data i.e., all `Person` and `Meeting` objects (which are contained in a `UniquePersonList` and `UniqueMeetingList` object).
- stores the currently 'selected' `Person` and `Meeting` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` and `ObservableList<Meeting>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
- stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
- does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>

### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,

- can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
- inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
- depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

---

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### View Contacts/Meetings feature

#### Implementation

Both the view contact command `viewc` and the view meeting command `viewm` are implemented in the exact same way due to the similarities between the `Person` and `Meeting` classes.

As such, this section shall only detail the implementation of the `viewc` command. However the implementation of `viewm` can be derived by replacing some `Person` related functions/classes/objects with its `Meeting` counterpart.

`viewc` and `viewm` both take in an index as their only argument. This refers to the `Person` or `Meeting` index respectively as displayed on either the Contact list or Meeting list.

When `viewc 2` is used, an instance of a `ViewContactCommand` (`ViewMeetingCommand` in the case of `viewm`) is created as shown in the following Sequence Diagram. This step does not differ from the way other commands have been shown to be created. The argument for our example would just be `2`, which would be stored as the `targetIndex` field of the `ViewContactCommand` object.

![ViewContactCommandSequenceDiagram](images/tracing/ViewContactCommandSequenceDiagram-View%20Contact%20Command%20Sequence.png)

Once the instance of `ViewContactCommand` is created, it is executed. During execution, the command stores the contents of its `targetIndex` field in the `ModelManager` using its `setViewedPersonIndex` method as shown in the next Sequence Diagram. For `ViewMeetingCommand` it would use the `setViewedMeetingIndex` method instead.

![StoreViewedItemsToModelDiagram](images/tracing/ViewCommandsSequenceDiagram-Store%20viewed%20Items%20to%20Model.png)

Once the indexes of the `Person` and `Meeting` objects to view (if any) are stored in `ModelManager`, their corresponding `Person` and `Meeting` objects (in this case the 2nd `Person` as displayed on the list) are obtained by the `MainWindow` as a `Pair` through the `getViewedItems` method of the `LogicManager` class. As such, both objects can then be forwarded to the `InfoDisplayPanel` using `setViewedModel`, which then displays detailed information of both objects. This process is denoted in the final Sequence Diagram below.

![ForwardViewedPersonMeetingtoUiDiagram](images/tracing/UiViewItemsSequenceDiagram-Forward%20Viewed%20Person%20&%20Meeting%20to%20Ui.png)

#### Design Considerations and Rationale

1. Passing viewed `Person` and `Meeting` from Model to Ui through Logic:
   - `ViewContactCommand` and `ViewMeetingCommand` only have access to the `ModelManager` while `MainWindow` only has access to `LogicManager`.
   - To prevent excessive and unnecessary coupling for the sake of two commands, it is deemed more worthwhile to use `LogicManager` as a proxy between `ModelManager` and `MainWindow`, especially since `LogicManager` already had access to `ModelManager`.
2. Storing the viewed `Person` and `Meeting` as fields in `ModelManager`:
   - The behaviour of `ModelManager` is not contradicted as it is already responsible for storing both the filtered lists of `Person` and `Meeting` objects that are displayed in the Ui.
3. Storing the `Index` of the viewed `Person` and `Meeting` rather than a copy of the objects directly:
   - Storing a copy of the objects was done initially but led to a display issue.
   - When the fields of any currently viewed item are edited, the display does not update as the copy of the original viewed item does not get updated as well.
   - Storing the `Index` fixes this issue as the `Person` and `Meeting` objects are only forwarded to the Ui after the execution of a command.
   - This does lead to a separate issue where deleting a `Person` or `Meeting` object might lead to the wrong item being displayed due to a change in displayed list index. A simple solution is to simply reset the viewed item in question to nothing until their respective view commands are used again.


### Find meeting feature

The find meeting command is facilitated by `GeneralMeetingPredicate` that by itself is the combined predicate for all the meeting data fields. It is placed within the Model component and is only dependent on other predicate classes and `Meeting`.

`findm` is supported by 5 sub-predicates that would search their respective fields.
- m/TITLE_KEYWORDS  —  Finds meetings which `Title` contain any of the keywords given using `TitleContainsKeywordsPredicate`.
- a/LOCATION_KEYWORDS  —  Finds meetings which `Location` contain any of the keywords given using `LocationContainsKeywordsPredicate`.
- n/ATTENDEE_KEYWORDS  —  Finds meetings which set of `Attendee` contain any of the keywords given using `AttendeeContainsKeywordsPredicate`.
- t/TAG_KEYWORDS  —  Finds meetings which set of `Tag` contain any of the keywords given using `TagContainsKeywordsPredicate`.
- s/START e/END  —  Finds meetings that fall within the range of time given by START & END using `MeetingTimeContainsPredicate`. (Both START & END must come together)

All of these fields are optional and typing `findm` alone will not impose any predicates, except MeetingTimeContainsPredicate which would find all meetings from 1st January of Year 1 A.D. to 31st December of Year 9999 A.D.

Given below is an example usage scenario and how the `findm` command behaves at each step.

Step 1. The user launches the application and the application loads the data from storage.
The existing objects are shown below. Objects not relevant to the behaviour are excluded.
![FindMeetingInitial](images/FindMeetingInitial.png)

Step 2. The user executes `findm m/meeting` command to find all meetings that have the keyword `meeting` in their title. This results in the logic component creating an `AddressBookParser` object to make a `FindMeetingCommandParser` object which will in turn create the predicate objects as well as the FindMeetingCommand object. The argument is broken down by `PREFIX` and for each `PREFIX` there is a String array of arguments broken down by whitespace. (s/START & e/END use `LocalDateTime` instead)
![FindMeetingSecond](images/FindMeetingSecond.png)

Step 3. The `FindMeetingCommand` will be immediately executed on the `FilteredList<Meeting>` object. The `GeneralMeetingPredicate` will be used on all meetings, meetings which pass all 5 predicates be shown in `MeetingSchedulePanel`. After which `FindMeetingCommand` and the predicate objects will no longer be referenced.
![FindMeetingLast](images/FindMeetingInitial.png)

The following diagrams show the entire sequence flow for `LogicManager#execute()` for FindMeetingCommand.
![FindMeetingSequence](images/FindMeetingSequence.png)
![FindMeetingSequenceParse](images/FindMeetingSequenceParse.png)
![FindMeetingSequenceExecute](images/FindMeetingSequenceExecute.png)


### Add attendee feature
User can specify a Person to add as an Attendee to a specified Meeting.

To avoid storing an entire `JsonAdaptedPerson` object within the `JsonAdaptedMeeting` every time a `Person` is added to a `Meeting`,
we created the `Attendee` class to store a unique identifier for the `Person` added.
As every `Person` has a unique name in the current iteration, `Attendee` is implemented in the following way:
- `Attendee(attendeeName)` -- Initialized with a String obtained from `Person.getName().toString()`
- `Attendee#getAttendeeName()` -- Returns a String representing the attendee's name

![AttendeeClassDiagram](images/AttendeeClassDiagram.png)

The following sequence diagram shows how the add attendee operation works:

![AddAttendeeSequenceDiagram](images/AddAttendeeSequenceDiagram.png)

A Person object can be obtained from a Meeting's list of attendees by searching through `UniquePersonList`
for a `Person` with a name matching `attendeeName`.


### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

- `VersionedAddressBook#commit()` — Saves the current address book state in its history.
- `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
- `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how the undo operation works:

![UndoSequenceDiagram](images/UndoSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

- **Alternative 1 (current choice):** Saves the entire address book.

  - Pros: Easy to implement.
  - Cons: May have performance issues in terms of memory usage.

- **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  - Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  - Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_

---

## **Documentation, logging, testing, configuration, dev-ops**

- [Documentation guide](Documentation.md)
- [Testing guide](Testing.md)
- [Logging guide](Logging.md)
- [Configuration guide](Configuration.md)
- [DevOps guide](DevOps.md)

---

## **Appendix: Requirements**

### Product scope

**Target user profile**:

- has many meetings to keep track of
- has a need to manage a significant number of contacts
- wants to organise meetings and contacts
- can type fast and is comfortable using CLI

**Value proposition**: manage and organise contacts and meetings faster than a mouse/GUI driven app

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`


| Priority | As a …​                                   | I want to …​                    | So that I can…​                       |
| -------- | ----------------------------------------- | ------------------------------- | ------------------------------------- |
| `[EPIC]` | agent who has meetings                    | have a meeting schedule         | keep track of them                    |
| `* * *`  | agent                                     | create new meetings             |                                       |
| `* * *`  | agent                                     | delete meetings                 |                                       |
| `* * *`  | agent                                     | view meetings                   |                                       |
| `* * *`  | agent                                     | view a specific meeting         | see more details                      |
| `* *`    | agent                                     | edit a meeting                  | change its details                    |
| `* *`    | agent                                     | sort my meetings by date        | see which ones come first             |
| `*`      | agent                                     | mark meetings as complete       | know which meetings are done          |
| `[EPIC]` | agent who has clients                     | have an address book            | keep track of them                    |
| `* * *`  | agent                                     | create new contacts             |                                       |
| `* * *`  | agent                                     | delete contacts                 |                                       |
| `* * *`  | agent                                     | view contacts                   |                                       |
| `* * *`  | agent                                     | view a specific contact         | see more details                      |
| `* *`    | agent                                     | edit a contact                  | change its details                    |
| `*`      | agent                                     | assign named tags to meetings   | organise meetings                     |
| `*`      | agent                                     | filter meetings by tags         | view related meetings together        |
| `[EPIC]` | agent who meets with clients              | schedule meetings with contacts | keep track of the client I am meeting |
| `* * *`  | agent                                     | add contacts to meetings        |                                       |
| `* * *`  | agent                                     | remove contacts from meetings   |                                       |
| `* * *`  | agent                                     | view contacts in meetings       |                                       |
| `*`      | agent who wants to meet clients regularly | know the last contacted date    | when to touch base with a client      |


_{More to be added}_

### Use case

**Use case: Add a contact to a meeting**

**MSS**

1.  User requests to list meetings.
2.  OutBook shows a list of meetings.
3.  User requests to list contacts.
4.  OutBook shows a list of contacts.
5.  User requests to add a specific contact to a specific meeting.
6.  OutBook adds the contact to the meeting.

    Use case ends.

**Extensions**

- 2a. The list of meetings is empty.

  Use case ends.

- 4a. The list of contacts is empty.

  Use case ends.

- 5a. The given meeting index is invalid.

  - 5a1. OutBook shows an error message.

    Use case resumes at step 2.

- 5b. The given contact index is invalid.

  - 5b1. OutBook shows an error message.

    Use case resumes at step 4.

- 5c. The contact is already in the meeting.

  - 5a1. OutBook shows an error message.

    Use case ends.

**Use case: Remove contact from a meeting**

**MSS**

1.  User requests to list meetings.
2.  OutBook shows a list of meetings.
3.  User requests to view details of a specific meeting.
4.  OutBook shows the details of the meeting. 
4.  User requests to remove a specific contact from the meeting.
5.  OutBook removes the contact from the meeting.

    Use case ends.

**Extensions**

- 2a. The list of meetings is empty.

  Use case ends.

- 3a. The given meeting index is invalid.

  - 3a1. OutBook shows an error message.

    Use case resumes at step 2.

- 4a. There are no contacts in the meeting.

  Use case ends.

- 5a. The given meeting index is invalid.

  - 5a1. OutBook shows an error message.

    Use case resumes at step 2.

- 5b. The given contact index is invalid.

  - 5b1. OutBook shows an error message.

    Use case resumes at step 3.


**Use case: Mark meeting as complete**

**MSS**

1. User requests to mark a specific meeting as complete
2. OutBook marks the specific meeting as complete
3. OutBook updates the last contacted date of attendees to the meeting date

   Use case ends. 

**Extensions**

- 1a. The given meeting index is invalid.

  - 1a1. OutBook shows an error message.

    Use case resumes from the start.

- 1b. The given meeting is already marked complete.

  - 1b1. OutBook shows an error message.

    Use case ends.

_{More to be added}_

### Non-Functional Requirements

**Performance**

1.  Should be able to respond to user input within 2 seconds under normal operating conditions.
2.  Should be able to handle a database of up to 1000 contacts and 500 meetings without a significant performance degradation.

**Reliability**

1.  Data integrity should be ensured under any usage conditions through automatic data backup.

**Usability**

1.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
2.  Application GUI should be intuitive wherever possible, to reduce training for new users.

**Documentation**

1.  User documentation should include a comprehensive user manual.
2.  Developer documentation should cover the architecture, code structure, and guidelines for future development.

**Compatibility**

1.  Should work on any _mainstream OS_ as long as it has Java `11` or above installed.

_{More to be added}_

### Glossary

- **User Interface (UI)**: The point of interaction between a user and a software application, with both graphical and non-graphical elements.
- **Application Programming Interface (API)**: A set of rules and tools allowing different software applications to communicate and exchange information.
- **Command Line Interface (CLI)**: A text-based interface for interacting with a computer program or operating system, where users enter commands.
- **Graphical User Interface (GUI)**: A visual interface using graphical elements like windows, icons, and buttons for user interaction with a software application.
- **Mainstream OS**: Windows, Linux, Unix, OS-X
- **Private contact detail**: A contact detail that is not meant to be shared with others

---

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
      Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
