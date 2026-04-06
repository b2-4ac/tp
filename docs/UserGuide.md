---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# PowerRoster User Guide

PowerRoster is a **desktop app built to help Freelance Personal Fitness Trainers manage clients.** It is a centralised client management system for trainers to keep track and access client information such as personal details, gym locations, and individual notes. It is optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI).

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2526S2-CS2103-F08-1a/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your PowerRoster.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar PowerRoster.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data. You can use `clear` if you want to remove all sample clients.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all clients.

   * `add n/John Tan g/M dob/24/12/1999 p/98765432 e/johnt@example.com a/123 Sengkang East, #01-01 l/ActiveSG @ Fernvale Square t/beginner` : Adds a client named `John Tan` to PowerRoster.

   * `delete 3` : Deletes the 3rd client shown in the current list.

   * `clear` : Deletes all clients.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Tan`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Tan t/beginner` or as `n/John Tan`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/beginner`, `t/teen t/intermediate` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `exit 123`, it will be interpreted as `exit`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help: `help`

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

* Names with forward slashes (e.g., "s/o" for "son of") are not supported due to the "/" being used as a command delimiter. You can replace the slashes with spaces (e.g., "Muthu s/o Rajan" can be entered as "Muthu s o Rajan".)
* Other names with non-alphanumeric characters like José Muñoz or 小明 should be replaced with suitable alphanumeric characters.
* `NAME` is case-sensitive for duplicate detection
* `EMAIL_ADDRESS` must be in `local-part@domain` format (e.g., `alex@example.com`):
  * Local-part supports letters/digits and `+_.-` (not at the start/end, and not consecutively)
  * Each domain label starts and ends with an alphanumeric character and may contain single hyphens in between (no consecutive hyphens)
  * The part after the final dot (e.g., `com` in `example.com`) must be at least 2 characters
* `GENDER` must be either `M` or `F` (case-insensitive)
* `DATE_OF_BIRTH` must follow the format 'DD/MM/YYYY'
* `DATE_OF_BIRTH` must be a valid date, not in the future, and not more than 100 years in the past.
* `LOCATION` can contain any value.
* If `LOCATION` is omitted, the client is treated as having no specified location and the UI displays `N/A`.
* Repeated use of the same non-tag prefix is not allowed (e.g., `n/Alex n/Jordan`). Repeating `t/` is allowed for multiple tags.

<box type="tip" seamless>

**Tip:** A client can have any number of tags (including 0)
</box>

Examples:
* `add n/John Tan g/M dob/24/12/1999 p/98765432 e/johnt@example.com a/123 Sengkang East, #01-01 l/ActiveSG @ Fernvale Square t/beginner`
* `add n/Danielle Lim g/F dob/13/06/2001 p/91234567 t/athlete e/danielle@example.com a/456 Jurong West, #02-02 l/GymBoxx @ JCube Mall`

### Listing all clients: `list`

Shows a list of all clients in PowerRoster.

Format: `list`

* `list` shows all clients by resetting any active filter, while preserving the current sort order (if any).

![List command showing all clients in the client list panel](images/listClientsResult.png)

### Viewing a client's full profile: `view`

Shows the full profile of a client in the Client Details panel.

Format: `view INDEX`

* Views the client at the specified `INDEX`. The index refers to the index number shown in the displayed client list. The index **must be a positive integer** 1, 2, 3, …​
* The detailed profile appears in the right-side Client Details panel.

![View command showing full profile in the Client Details panel](images/viewClientProfileResult.png)

Examples:
* `view 1` shows the full profile of the 1st client in the current list.
* `find Alex` followed by `view 1` shows the full profile of the 1st client in the filtered results.

### Editing a client: `edit`

Edits an existing client in PowerRoster.

Format: `edit INDEX [n/NAME] [g/GENDER] [dob/DATE_OF_BIRTH] [p/PHONE] [e/EMAIL] [a/ADDRESS] [l/LOCATION] [t/TAG]…​`

* Edits the client at the specified `INDEX`. The index refers to the index number shown in the displayed client list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* Feedback (e.g. `added/updated` or `unchanged`) is shown per specified field.
* If multiple provided field values are invalid in one command, all related validation errors are shown together.
* Repeated use of the same non-tag prefix is not allowed (e.g., `p/91234567 p/98765432`). Repeating `t/` is allowed for multiple tags.
* When editing tags, the existing tags of the client will be removed i.e adding of tags is not cumulative.
* You can remove all the client’s tags by typing `t/` without specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st client to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Dylan Lim t/` Edits the name of the 2nd client to be `Dylan Lim` and clears all existing tags.

### Adding notes to a client: `note`

Adds/appends a note to an existing client in PowerRoster.

Format: `note INDEX n/NOTE` or `note INDEX a/NOTE`

* Adds/appends a note to the client at the specified `INDEX`. The index refers to the index number shown in the displayed client list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of `n/NOTE` and `a/NOTE` must be provided, but not both.
* Repeated use of the same prefix (e.g., `n/first n/second`) is not allowed.
* `n/NOTE` adds a note to the client. Any existing notes will be replaced by the new note.
* `a/NOTE` appends a note to the client's existing notes (if any) with a space in between. If the client has no existing notes, `a/NOTE` behaves the same as `n/NOTE`.
* You can delete an existing client note by typing `n/` without specifying any note after it.
* There is no limit to the number of characters in a note.

Examples:
* `note 1 n/Prefers morning sessions.` adds the note `Prefers morning sessions.` to the 1st client, replacing any existing notes.
* If the 2nd client currently has note `Prefers morning sessions.`, `note 2 a/Just recovered from a knee injury.` updates it to `Prefers morning sessions. Just recovered from a knee injury.` (a space is inserted before the appended note).

### Assigning a client's workout programme: `plan`

Assigns/unassigns the workout programme of an existing client in PowerRoster.

Format: `plan INDEX wp/PLAN_CATEGORY`

* Assigns/unassigns the workout programme of the client at the specified `INDEX`. The index refers to the index number shown in the displayed client list. The index **must be a positive integer** 1, 2, 3, ...
* The `wp/` prefix is required.
* `PLAN_CATEGORY` must be one of: `PUSH`, `PULL`, `LEGS`, `CORE`, `CARDIO`, `MOBILITY`, `FULL BODY`, `CONDITIONING` (case-insensitive).
* For multi-word categories, spaces, hyphens, and underscores are all accepted as separators.
* Entering `wp/` with no value unassigns the client's assigned workout programme.
* Duplicate `wp/` prefixes are not allowed.
* Workout programmes can only be changed using `plan` (not `edit`).

Examples:
* `plan 1 wp/PUSH` assigns the 1st client to the `PUSH` programme.
* `plan 2 wp/full body` assigns the 2nd client to the `FULL BODY` programme.
* `plan 3 wp/` unassigns the 3rd client's workout programme.

### Setting a client's session rate: `rate`

Sets/clears the session rate of an existing client in PowerRoster.

Format: `rate INDEX r/RATE`

* Sets/clears the rate of the client at the specified `INDEX`. The index refers to the index number shown in the displayed client list. The index **must be a positive integer** 1, 2, 3, …​
* `RATE` must be either blank or a non-negative monetary value with up to 2 decimal places.
* Valid examples include `120`, `120.5`, `120.50`, `.50`, and `120.`.
* Invalid examples include `-10`, `1,000`, `100.000`, `$100`.
* Entering `r/` with no value clears the client's existing rate.
* Repeated use of the same prefix (e.g., `r/120 r/130`) is not allowed.
* Client rates can only be changed using `rate` (not `edit`).

Examples:
* `rate 1 r/120` sets the 1st client's rate to `120.00`.
* `rate 2 r/80.5` sets the 2nd client's rate to `80.50`.
* `rate 3 r/` clears the 3rd client's rate.

### Setting a client's body measurements: `measure`

Sets/clears body measurements of an existing client in PowerRoster.

Format: `measure INDEX [h/HEIGHT_CM] [w/WEIGHT_KG] [bf/BODY_FAT_PERCENTAGE]`

* Sets/clears one or more body measurements of the client at the specified `INDEX`. The index refers to the index number shown in the displayed client list. The index **must be a positive integer** 1, 2, 3, ...
* At least one of `h/`, `w/`, or `bf/` must be provided.
* `measure INDEX` without any of `h/`, `w/`, or `bf/` is invalid (e.g., `measure 1`).
* `HEIGHT_CM` must be either blank or a number in cm between `50.0` and `300.0`, with up to 1 decimal place.
* `WEIGHT_KG` must be either blank or a number in kg between `20.0` and `500.0`, with up to 1 decimal place.
* `BODY_FAT_PERCENTAGE` must be either blank or a number between `1.0` and `75.0`, with up to 1 decimal place.
* Values like `170.` are accepted.
* Entering `h/`, `w/`, or `bf/` with no value clears that specific measurement.
* Repeated use of the same prefix (e.g., `h/175.0 h/180.0`) is not allowed.
* Measurements can only be changed using `measure` (not `edit`).

Examples:
* `measure 1 h/175.5` sets the 1st client's height to `175.5`.
* `measure 2 w/72.0 bf/14.8` sets the 2nd client's weight and body fat percentage.
* `measure 3 h/ w/ bf/` clears all three measurements for the 3rd client.
* `measure 4 w/` clears the 4th client's weight.

### Changing a client's status: `status`

Changes the status of an existing client in PowerRoster between active and inactive.

Format: `status INDEX s/STATUS`

* Changes the status of the client at the specified `INDEX`, where `INDEX` is the index shown in the displayed client list and **must be a positive integer** 1, 2, 3, …​
* `STATUS` must be either `active` or `inactive` (case-insensitive).
* Repeated use of the same prefix is not allowed (e.g., `status 1 s/active s/inactive`) and will result in an error.
* If the client's status is already set to the specified value, a message will be shown and no change will be made.
* New clients are automatically set to `active` status when added.
* Use this feature to mark clients as inactive while retaining their records for future reference.

Examples:
* `status 1 s/inactive` changes the 1st client's status to inactive.
* `status 2 s/active` changes the 2nd client's status to active.
* `status 3 s/INACTIVE` changes the 3rd client's status to inactive (case-insensitive).

### Locating clients by name: `find`

Finds clients whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Clients matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`
* You can run `sort` after `find` to sort only the matching results.
* If a sort order is currently active, it is preserved in the find results.

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`

![Find command result showing matched clients](images/findResult.png)

### Filtering clients by location: `filter`

Filters clients whose gym location contains any of the given location phrase(s).

Format: `filter l/LOCATION_PHRASE [l/MORE_LOCATION_PHRASES]...`

* At least one `l/` prefix must be provided.
* The search is case-insensitive. e.g. specifying `l/Clementi` will match `Clementi ActiveSG Gym`.
* Each `l/` prefix is treated as one location phrase. e.g. `filter l/Anytime Fitness Jurong` will use `Anytime Fitness Jurong` as a phrase to match.
* Multiple `l/` prefixes are supported and clients matching at least one phrase are returned (i.e. `OR` search).
* Extra spaces within a phrase are normalised. e.g. `filter l/Anytime   Fitness` behaves the same as `filter l/Anytime Fitness`.
* Entering `l/` with no value filters clients with no specified location.
* If multiple `l/` prefixes are provided, each must have a non-blank value. e.g. `filter l/Clementi l/` is invalid.
* If a sort order is currently active, it is preserved in the filter results.

Examples:
* `filter l/Clementi` returns all clients whose locations contain the phrase `Clementi` such as `Clementi ActiveSG Gym` and `Anytime Fitness Clementi`.
* `filter l/Anytime Fitness Jurong` returns all clients whose locations contain the phrase `Anytime Fitness Jurong` such as `Anytime Fitness Jurong East` but not `Anytime Fitness Clementi` or `Jurong Point ActiveSG Gym`.
* `filter l/Anytime Fitness l/Clementi` returns clients whose locations contain `Anytime Fitness` or `Clementi`.
* `filter l/` returns clients with no specified location.

![Filter command result showing location-filtered clients](images/filterResult.png)

### Sorting clients: `sort`

Sorts the client list by a specified attribute in ascending or descending order.

Format: `sort ATTRIBUTE/ [o/ORDER]`

* Sorts the **currently displayed** client list by the specified attribute. If a filter is active, only the filtered results are sorted; if no filter is active, the entire client list is sorted.
* Only one sorting attribute (`n/`, `l/`, `dob/`, `p/`, `e/`, `a/`, `g/`, `s/`, `wp/`, or `r/`) can be specified at a time.
* The sorting attribute prefix must be provided without a value (e.g., `sort n/` is valid, `sort n/Alex` is invalid).
* The order parameter is optional and defaults to ascending (`asc`) if not specified.
* Repeated use of the same prefix is not allowed (e.g., `sort n/ n/` or `sort n/ o/asc o/desc`) and will result in an error.
* Once set, the sort order persists after `list`, `find`, and `filter` commands until replaced by another `sort` command.
* Supported attributes:
  * `n/` - Sort by name
  * `l/` - Sort by location
  * `dob/` - Sort by date of birth
  * `p/` - Sort by phone number
  * `e/` - Sort by email address
  * `a/` - Sort by address
  * `g/` - Sort by gender
  * `s/` - Sort by status (active before inactive)
  * `wp/` - Sort by workout plan (alphabetical)
  * `r/` - Sort by session rate (clients with no rate set sort first)
* Order options:
  * `o/asc` - Ascending order (A to Z, earliest to latest, 0 to 9)
  * `o/desc` - Descending order (Z to A, latest to earliest, 9 to 0)
* The attribute prefix and the order prefix must be separated by a space. For example, `sort n/ o/desc` is valid, but `sort n/o/desc` is not and will result in an error.
* Clients with no location set are always sorted to the **end** of the list in ascending order, and to the **top** of the list in descending order, so they do not interleave with real location names.

Examples:
* `sort n/` sorts the displayed client list by name in ascending order (A to Z).
* `sort n/ o/desc` sorts the displayed client list by name in descending order (Z to A).
* `sort dob/ o/asc` sorts the displayed client list by date of birth in ascending order (oldest to youngest).
* `sort l/` sorts the displayed client list by gym location in ascending order.
* `sort s/` sorts the displayed client list by status, active clients first.
* `sort r/ o/desc` sorts the displayed client list by session rate, highest rate first.
* `sort wp/` sorts the displayed client list by workout plan alphabetically.
* `filter l/ActiveSG` followed by `sort n/` filters clients at ActiveSG locations, then sorts only those filtered results by name.

![Sort command result showing reordered client list](images/sortResult.png)


### Deleting a client: `delete`

Deletes the specified client from PowerRoster.

Format: `delete INDEX`

* Deletes the client at the specified `INDEX`.
* The index refers to the index number shown in the displayed client list.
* The index **must be a positive integer** 1, 2, 3, …​
* Deleting a client also deletes all workout logs linked to that client.

Examples:
* `list` followed by `delete 2` deletes the 2nd client in PowerRoster.
* `find Betsy` followed by `delete 1` deletes the 1st client in the results of the `find` command.

### Logging a workout session: `log`
Logs a workout for the specified client.

Format: `log INDEX [time/TIME] [l/LOCATION]`

* Logs a workout session for the client at the specified `INDEX`
* The index refers to the index number shown in the displayed client list.
* The index **must be a positive integer** 1, 2, 3, ...
* `TIME` must be a valid date and time not in the future, nor more than 50 years in the past.
* `TIME` must be in the format: "DD/MM/YYYY HH:mm"
* If `TIME` is not declared, the current time will be used.
* `LOCATION` can take on any value.
* If `LOCATION` is not specified, the client's preset location will be used.
* A duplicate workout log for the same client at the same date and time is not allowed.
* Repeated use of the same prefix (e.g., `time/01/01/2026 10:00 time/02/01/2026 10:00` or `l/Gym A l/Gym B`) is not allowed.

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
* If the client has no logged workouts, a message is shown.

Examples:
* `last 5` Retrieves the Date and Location of the last workout for the fifth client in the displayed list.

### Clearing all clients: `clear`

Clears all clients and workout logs from PowerRoster.

Format: `clear`

### Exiting the program: `exit`

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

### Archiving data files `[coming soon]`

_Details coming soon ..._

### Viewing all workout logs `[coming soon]`
* Users will be able to view the entire workout log history of a specified client.

_Details coming soon ..._

### Editing and Deleting workout logs `[coming soon]`
* Users will be able to edit and delete workout logs.

_Details coming soon ..._

### More information to be recorded in workout logs `[coming soon]`
* Workout Logs will hold more information regarding the specific workout. (E.g. Exercises, Sets, Reps etc...)

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
**Add**    | `add n/NAME g/GENDER dob/DATE_OF_BIRTH p/PHONE_NUMBER e/EMAIL_ADDRESS a/ADDRESS [l/LOCATION] [t/TAG]…​` <br> e.g., `add n/John Tan g/M dob/24/12/1999 p/98765432 e/johnt@example.com a/123 Sengkang East, #01-01 l/ActiveSG @ Fernvale Square t/beginner`
**Clear**  | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Log**    | `log INDEX [time/TIME] [l/LOCATION]` <br> e.g., `log 2 l/Sengkang ActiveSG Gym`
**Last**   | `last INDEX` <br> e.g., `last 5`
**Edit**   | `edit INDEX [n/NAME] [g/GENDER] [dob/DATE_OF_BIRTH] [p/PHONE] [e/EMAIL] [a/ADDRESS] [l/LOCATION] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Note**   | `note INDEX n/NOTE` or `note INDEX a/NOTE`<br> e.g., `note 1 n/Prefers morning sessions.`
**Plan**   | `plan INDEX wp/PLAN_CATEGORY`<br> e.g., `plan 1 wp/PUSH`, `plan 2 wp/FULL BODY`, `plan 3 wp/`
**Rate**   | `rate INDEX r/RATE`<br> e.g., `rate 1 r/120.50`, `rate 2 r/`
**Measure**| `measure INDEX [h/HEIGHT_CM] [w/WEIGHT_KG] [bf/BODY_FAT_PERCENTAGE]`<br> e.g., `measure 1 h/175.5 w/72.0 bf/14.8`, `measure 2 h/`
**Status** | `status INDEX s/STATUS`<br> e.g., `status 1 s/inactive`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**Filter** | `filter l/LOCATION_PHRASE [l/MORE_LOCATION_PHRASES]...`<br> e.g., `filter l/Clementi`, `filter l/Anytime Fitness l/Clementi`, `filter l/`
**Sort**   | `sort ATTRIBUTE/ [o/ORDER]`<br> e.g., `sort n/`, `sort dob/ o/desc`, `sort r/ o/desc`, `sort s/`, `sort wp/`
**List**   | `list`
**View**   | `view INDEX`<br> e.g., `view 1`
**Exit**   | `exit`
**Help**   | `help [COMMAND_WORD]`<br> e.g., `help`, `help add`
