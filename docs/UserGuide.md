---
layout: page
title: OutBook User Guide
---

OutBook is an app that allows busy professionals to manage their numerous contacts and meeting schedule.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

# Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `OutBook.jar` from [here](https://github.com/AY2324S1-CS2103T-F12-4/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for OutBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar OutBook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `listc` : Lists all contacts.

   * `addc n/John Doe p/98765432 e/johnd@example.com l/10.10.2023 1000 o/NUS` : Adds a contact named `John Doe` to OutBook.

   * `deletec 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts and meetings.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

# Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

## Contact commands

### Adding a contact: `addc`

Adds a contact to OutBook.

Format: `addc n/NAME p/PHONE_NUMBER e/EMAIL l/LAST_CONTACTED_TIME s/STATUS [t/TAG]…​`

* NAME, PHONE_NUMBER, EMAIL and LAST_CONTACTED_TIME are compulsory fields. STATUS and TAG are optional.
* PHONE_NUMBER must contain only numbers, and be at least 3 digits long.
* EMAIL must be of the format local-part@domain and adhere to the following constraints:
  1. The local-part should only contain alphanumeric characters and these special characters, excluding the parentheses, (+_.-).
  2. The local-part may not start or end with any special characters.
  3. The domain name is made up of domain labels separated by periods.
     The domain name must:
      - end with a domain label at least 2 characters long
      - have each domain label start and end with alphanumeric characters
      - have each domain label consist of alphanumeric characters, separated only by hyphen
* LAST_CONTACTED_TIME must contain both date and time and adhere to the dd.MM.yyyy HHmm format.
  -  For instance, 1st October 2023, 10:00am will be written as 01.10.2023 1000.
* STATUS, if included, must be one of { NIL, Prospective, Active, Inactive, Claimant, Renewal } or blank.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

Examples:
* `addc n/John Doe p/98765432 e/johnd@example.com l/01.10.2023 1000`
* `addc n/Betsy Crowe t/friend e/betsycrowe@example.com p/1234567 l/01.01.2023 0100 t/Professor`


### Listing all persons : `listc`

Shows a list of all contacts in OutBook.

Format: `listc`


### Deleting a person : `deletec`

Deletes the specified person from OutBook.

Format: `deletec INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `listc` followed by `delete 2` deletes the 2nd person in the results of the `listc` command.
* `findc Betsy` followed by `delete 1` deletes the 1st person in the results of the `findc` command.


### Editing a contact : `editc`

Edits an existing contact in OutBook.

Format: `editc INDEX [n/NAME] [p/PHONE] [e/EMAIL] [l/LAST_CONTACTED_TIME] [s/STATUS] [t/TAG]…​`

* Edits the contact at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* All fields are optional, but at least one must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `editc 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `editc 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.


### Viewing detailed contact information : `viewc`

Views detailed information of a contact in OutBook.

Format: `viewc INDEX`

* Views detailed information of the contact at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
* Displays contact Name, Phone, Email, Last Contacted Time, Status, Remarks and Tags.

Examples:
* `viewc 2` Displays detailed information related to the 2nd contact on the list.


### Locating persons by name: `findc`

Find contacts whose names contain any of the given keywords.

Format: `findc KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `findc John` returns `john` and `John Doe`
* `findc alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)


## Meeting commands

### Adding a meeting: `addm`

Adds a meeting to OutBook.

Format: `addm m/TITLE a/LOCATION s/START e/END`

* START and END must contain both date and time and adhere to the dd.MM.yyyy HHmm format.
  -  For instance, 1st October 2023, 10:00am will be written as 01.10.2023 1000.

Examples:
* `addm m/Lunch a/Cafeteria s/20.09.2023 1200 e/20.09.2023 1300`
* `addm m/CS2103T meeting a/Zoom call url s/20.09.2023 1000 e/20.09.2023 1200`


### Listing all meetings : `listm`

Shows a list of all meetings in OutBook.

Format: `listm`


### Deleting a meeting : `deletem`

Deletes the specified meeting from OutBook.

Format: `deletem INDEX`

* Deletes the meeting at the specified `INDEX`.
* The index refers to the index number shown in the displayed meeting list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `listm` followed by `deletem 2` deletes the 2nd meeting in the results of the `listm` command.
* `findm m/Project` followed by `deletem 1` deletes the 1st meeting in the results of the `findm` command.


### Editing a meeting : `editm`

Edits an existing meeting in OutBook.

Format: `editm INDEX [m/TITLE] [a/LOCATION] [s/START] [e/END] [t/TAG]…​`

* Edits the meeting at the specified `INDEX`. The index refers to the index number shown in the displayed meeting list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
  specifying any tags after it.

Examples:
*  `editm 1 a/Hawker Centre s/15.09.2023 1500` Edits the location and start of the 1st meeting to be `Hawker Centre` and `15.09.2023 1500` respectively.
*  `editm 2 m/Zoom meeting t/` Edits the title of the 2nd meeting to be `Zoom meeting` and clears all existing tags.


### Viewing detailed meeting information : `viewm`

Views detailed information of a meeting in OutBook.

Format: `viewm INDEX`

* Views detailed information of the meeting at the specified `INDEX`.
* The index refers to the index number shown in the displayed meeting list.
* The index **must be a positive integer** 1, 2, 3, …​
* Displays meeting Title, Location, Meeting times, Attendees and Tags.

Examples:
* `viewm 2` Displays detailed information related to the 2nd meeting on the list, including current participants.


### Locating meetings by name: `findm`

Find meetings for which all respective field (Title, location, attendee...) given by the user, contain at least 1 keyword, as well as happen within the duration given (start & end).

Format: `findm [m/KEYWORDS] [a/KEYWORDS] [t/KEYWORDS] [n/ATTENDEENAME] [s/START e/END]`

* The search is case-insensitive. e.g `shop` will match `SHOP`
* The order of the keywords does not matter. e.g. `Shop Meet` will match `Meet Shop`
* Title, location, tags and attendees are searched, within the time frame given by START and END
* Only full words will be matched e.g. `Meet` will not match `Meeting`
* For a single field, a Meeting must match at least one keyword will be returned (i.e. `OR` search).
  e.g. `Shop Meet` will return `Meeting: Shop at mall`, `Meeting: Meet client`
* If there are multiple fields the meeting must match at least one keyword for each field to be returned (i.e. `AND` search).
    e.g. `m/Shop Meet a/Mall` will return `Meeting: Shop at mall, Location:Mall`

Examples:
* `findm m/project` returns `project` and `Project work`
* `findm m/zoom meeting` returns `Zoom session`, `Zoom meeting`, `Meeting advisor`
* `findm s/09.09.2023 0000 e/09.10.2023 0000` returns all meetings between 09.09.2023 0000 and 09.10.2023 0000<br>
* `findm m/Meeting s/09.09.2023 0000 e/09.10.2023 0000` returns `Zoom meeting`, `Meeting advisor`, if these meetings start after 09.09.2023 0000 and end before 09.10.2023 0000<br>
  ![result for 'findAlexMeeting'](images/findAlexMeetingResult.png)

### Add contact to meeting: `addmc`

Adds a contact to a meeting as an attendee.

Format: `addmc MEETING_INDEX CONTACT_INDEX`

* Adds a contact at the specified `CONTACT_INDEX` to a meeting at the specified `MEETING_INDEX`.
* `MEETING_INDEX` refers to the index number shown in the displayed meeting list.
* `CONTACT_INDEX` refers to the index number shown in the displayed contact list.
* The index **must be a positive integer** 1, 2, 3, …​
* Both `MEETING_INDEX` & `CONTACT_INDEX` must refer to the index of an existing meeting or contact respectively.
* Contact name will be listed in the detailed description of meetings when `viewm` is used.

Examples:
* `addmc 3 1` adds the 1st contact as an attendee to the 3rd meeting in OutBook.

### Remove contact from meeting: `rmmc`

Removes a contact from a meeting.

Format: `rmmc MEETING_INDEX ATTENDEE_INDEX`

* Removes a contact at the specified `ATTENDEE_INDEX` to the meeting at the specified `MEETING_INDEX`.
* `MEETING_INDEX` refers to the index number shown in the displayed meeting list.
* `ATTENDEE_INDEX` refers to the index number of the attendee as shown in `viewm`.
* The index **must be a positive integer** 1, 2, 3, …​
* Both `MEETING_INDEX` & `ATTENDEE_INDEX` must refer to the index of an existing meeting or attendee.

Examples:
* `rmmc 3 2` removes the 2nd attendee from the 3rd meeting in OutBook.

### Clearing all entries : `clear`

Clears all entries from the displayed list in OutBook.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

OutBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

OutBook data are saved automatically as a JSON file `[JAR file location]/data/outbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, OutBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.
</div>


--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous OutBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action                          | Format, Examples                                                                                                                                                                             |
|---------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add contact**                 | `addc n/NAME p/PHONE_NUMBER e/EMAIL l/LAST_CONTACTED_TIME o/ORGANISATION [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com l/09.09.2023 0000 o/NUS t/friend t/colleague` |
| **Add contact to meeting**      | `addmc MEETING_INDEX CONTACT_INDEX` <br> e.g., `addmc 2 1`                                                                                                                                   |
| **Add meeting**                 | `addc m/TITLE a/LOCATION s/START e/END` <br> e.g., `addm m/Lunch a/Cafeteria s/20.09.2023 1200 e/20.09.2023 1300`                                                                            |
| **Clear**                       | `clear`                                                                                                                                                                                      |
| **Delete contact**              | `deletec INDEX` <br> e.g., `deletec 3`                                                                                                                                                       |
| **Delete meeting**              | `deletem INDEX` <br> e.g., `deletem 3`                                                                                                                                                       |
| **Edit contact**                | `editc INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [l/LAST_CONTACTED_TIME] [o/ORGANISATION] [t/TAG]…​`<br> e.g.,`editc 2 n/James Lee e/jameslee@example.com`                                    |
| **Find contact**                | `findc KEYWORD [MORE_KEYWORDS]` <br> e.g., `findc James Jake`                                                                                                                                |
| **Find meeting**                | `findm [m/KEYWORDS] [a/KEYWORDS] [t/KEYWORDS] [n/ATTENDEENAME] [s/START e/END]` <br> e.g., `findm m/Zoom Meet s/09.09.2023 0000 e/09.10.2023 0000`                                           |
| **Help**                        | `help`                                                                                                                                                                                       |
| **List contacts**               | `listc`                                                                                                                                                                                      |
| **List meetings**               | `listm`                                                                                                                                                                                      |
| **Remove contact from meeting** | `rmmc MEETING_INDEX ATTENDEE_INDEX` <br> e.g., `rmmc 2 2`                                                                                                                                    |
| **View contact details**        | `viewc INDEX` <br> e.g., `viewc 4`                                                                                                                                                           |
| **View meeting details**        | `viewm INDEX` <br> e.g., `viewm 4`                                                                                                                                                           |
