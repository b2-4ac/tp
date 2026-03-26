---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# PowerRoster User Guide

PowerRoster is a **desktop app built to help Freelance Personal Fitness Trainers manage client contacts.** It is a centralised client management system for trainers to keep track and access client information such as personal details, gym locations, and individual notes. It is optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI).

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2526S2-CS2103-F08-1a/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your PowerRoster.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar powerroster.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe g/M dob/24/12/1999 p/98765432 e/johnd@example.com a/John street, block 123, #01-01 l/ActiveSG @ Fernvale Square` : Adds a contact named `John Doe` to the PowerRoster.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `exit 123`, it will be interpreted as `exit`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows program usage instructions for all commands or a specific command.

![help message](images/helpMessage.png)

Format: `help [COMMAND_WORD]`

* `help` shows usage instructions for all available commands.
* `help COMMAND_WORD` shows usage instructions for the specified command only.
* The command word is case-insensitive. e.g. `help ADD` works the same as `help add`

Examples:
* `help` shows all available commands and their usage
* `help add` shows only the usage instructions for the add command
* `help filter` shows only the usage instructions for the filter command


### Adding a client: `add`

Adds a client to PowerRoster.

Format: `add n/NAME g/GENDER dob/DATE_OF_BIRTH p/PHONE_NUMBER e/EMAIL_ADDRESS a/ADDRESS [l/LOCATION] [t/TAG]…​​`

<box type="tip" seamless>

**Tip:** A client can have any number of tags (including 0)
</box>

Examples:
* `add n/John Tan g/M dob/24/12/1999 p/98765432 e/johnt@example.com a/123 Sengkang East, #01-01 l/ActiveSG @ Fernvale Square`
* `add n/Dave Lim g/F dob/13/06/2001 t/athlete e/davelim@example.com a/456 Jurong West, #02-02 l/GymBoxx @ JCube Mall`

### Listing all clients : `list`

Shows a list of all clients in PowerRoster.

Format: `list`

### Editing a client : `edit`

Edits an existing client in PowerRoster.

Format: `edit INDEX [n/NAME] [g/GENDER] [dob/DATE_OF_BIRTH] [p/PHONE] [e/EMAIL] [a/ADDRESS] [l/LOCATION] [t/TAG]…​`

* Edits the client at the specified `INDEX`. The index refers to the index number shown in the displayed client list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the client will be removed i.e adding of tags is not cumulative.
* You can remove all the client’s tags by typing `t/` without specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st client to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Dylan Lim t/` Edits the name of the 2nd client to be `Dylan Lim` and clears all existing tags.

### Adding notes to a client : `note`

Adds / appends a note to an existing client in PowerRoster.

Format: `note INDEX n/NOTE` or `note INDEX a/NOTE`

* Adds/appends a note to the client at the specified `INDEX`. The index refers to the index number shown in the displayed client list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of `n/NOTE` and `a/NOTE` must be provided, but not both.
* `n/NOTE` adds a note to the client. Any existing notes will be replaced by the new note.
* `a/NOTE` appends a note to the client's existing notes (if any) with a space in between. If the client has no existing notes, `a/NOTE` behaves the same as `n/NOTE`.
* You can delete an existing client note by typing `n/` without specifying any note after it.
* There is no limit to the number of characters in a note.

Examples:
* `note 1 n/Prefers morning sessions.` adds the note `Prefers morning sessions.` to the 1st client, replacing any existing notes.
* `note 2 a/Just recovered from a knee injury.` appends the note `Just recovered from a knee injury.` to the 2nd client’s existing notes. If the 2nd client has no existing notes, this behaves the same as `n/Just recovered from a knee injury.`.

### Locating clients by name: `find`

Finds clients whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Filtering clients by location: `filter`

Filters clients whose gym location contain any of the given location phrase(s).

Format: `filter l/LOCATION_PHRASE [l/MORE_LOCATION_PHRASES]...`

* At least one `l/` prefix must be provided.
* The search is case-insensitive. e.g. specifying `l/Clementi` will match `Clementi ActiveSG Gym`.
* Each `l/` prefix is treated as one location phrase. e.g. `filter l/Anytime Fitness Jurong` will use `Anytime Fitness Jurong` as a phrase to match. 
* Multiple `l/` prefixes for multiple location phrases is supported and clients matching at least one phrase will be returned (i.e. `OR` search). e.g. `filter l/Anytime Fitness l/Jurong` will use `Anytime Fitness` and `Jurong` as separate phrases to match.
* Extra spaces within a phrase are normalised. e.g. `filter l/Anytime   Fitness` will be treated as `filter l/Anytime Fitness`.
* Blank prefixed values are considered invalid. e.g. `filter l/` is invalid or `filter l/ l/` is invalid.

Examples:
* `filter l/Clementi` returns all clients whose locations contain the phrase `Clementi` such as `Clementi ActiveSG Gym` and `Anytime Fitness Clementi`.
* `filter l/Anytime Fitness Jurong` returns all clients whose locations contain the phrase `Anytime Fitness Jurong` such as `Anytime Fitness Jurong East` but not `Anytime Fitness Clementi` or `Jurong Point ActiveSG Gym`.
* `filter l/Anytime Fitness l/Jurong` returns all clients whose locations contain the phrase `Anytime Fitness` or `Jurong` such as `Anytime Fitness Jurong East`, `Anytime Fitness Clementi`, `Jurong Point ActiveSG Gym` but not `Clementi ActiveSG Gym`.


### Deleting a client : `delete`

Deletes the specified client from PowerRoster.

Format: `delete INDEX`

* Deletes the client at the specified `INDEX`.
* The index refers to the index number shown in the displayed client list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd client in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st client in the results of the `find` command.

### Logging a workout session : `log`
Logs a workout for the specified client.

Format: `log INDEX [time/TIME] [l/LOCATION]`

* Logs a workout session for the client at the specified `INDEX`
* The index refers to the index number shown in the displayed client list.
* The index **must be a positive integer** 1, 2, 3, ...
* If `TIME` is not declared, the current time will be used.
* If `LOCATION` is not specified, the client's preset location will be used.

Examples:
* `log 1` Logs a workout for the first client in the displayed list using the current time and their specified location.
* `log 3 time/26/03/2026 14:18` Logs a workout for the third client in the displayed list using their specified location and 26/03/2026 14:18 as the workout time.
* `log 2 l/Sengkang ActiveSG Gym` Logs a workout for the second client in the displayed list using the current time with the location set to "Sengkang ActiveSG Gym"

### Retrieving the most recent session: `last`
Retrieves the details of the most recent workout for the specified client.

Format: `last INDEX`

* Retrieves details of the most recent workout for the client at the specified `INDEX`
* The index refers to the index number shown in the displayed client list.
* The index **must be a positive integer** 1, 2, 3, ...

Examples:
* `last 5` Retrieves the Date and Location of the last workout for the fifth client in the displayed list.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

PowerRoster data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

PowerRoster data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, PowerRoster will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the PowerRoster to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous PowerRoster home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME g/GENDER dob/DATE_OF_BIRTH p/PHONE_NUMBER e/EMAIL_ADDRESS a/ADDRESS [l/LOCATION] [t/TAG]…​` <br> e.g., `add n/James Ho g/M dob/09/12/1977 p/92224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/beginner`
**Clear**  | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit**   | `edit INDEX [n/NAME] [g/GENDER] [dob/DATE_OF_BIRTH] [p/PHONE] [e/EMAIL] [a/ADDRESS] [l/LOCATION] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Note**   | `note INDEX n/NOTE` or `note INDEX a/NOTE`<br> e.g., `note 1 n/Prefers morning sessions.`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**Filter** | `filter l/LOCATION_PHRASE [l/MORE_LOCATION_PHRASES]...`<br> e.g., `filter l/Clementi l/ActiveSG`
**List**   | `list`
**Help**   | `help [COMMAND_WORD]`<br> e.g., `help`, `help add`
