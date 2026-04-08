---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# PowerRoster Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**
This project is adapted from [AddressBook-Level3](https://se-education.org/addressbook-level3/) by the [SE-EDU initiative](https://se-education.org).

The team also used GitHub Copilot for its auto-complete assistance during development.

PowerRoster relies on the following third-party libraries/frameworks:

* [JavaFX](https://openjfx.io/) for the GUI.
* [Jackson](https://github.com/FasterXML/jackson) for JSON serialization/deserialization.
* [JUnit 5](https://junit.org/junit5/) for automated testing.

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/ay2526s2-cs2103-f08-1a/tp/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/ay2526s2-cs2103-f08-1a/tp/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/ay2526s2-cs2103-f08-1a/tp/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g. `CommandBox`, `ResultDisplay`, `PersonListPanel`, `PersonDetailPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFX UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/ay2526s2-cs2103-f08-1a/tp/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/ay2526s2-cs2103-f08-1a/tp/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.
* renders a split layout where `PersonListPanel` shows a compact summary list while `PersonDetailPanel` displays full details for a selected client via the `view` command.

### Logic component

**API** : [`Logic.java`](https://github.com/ay2526s2-cs2103-f08-1a/tp/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

The sequence diagram below shows an example of a profile field update command — `execute("status 1 s/inactive")`. Commands such as `note`, `rate`, `plan`, and `measure` follow the same flow.

<puml src="diagrams/StatusSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `status 1 s/inactive` Command" />

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a client).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.
    - `CommandResult` can optionally carry a `Person` to be displayed in the UI detail panel. This is used by commands such as `view` that trigger a UI detail-view update without modifying model data.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/ay2526s2-cs2103-f08-1a/tp/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" />


The `Model` component,

* stores each PowerRoster client data as `Person` objects (contained in a `UniquePersonList`object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents user preferences, exposed as a `ReadOnlyUserPref` object.
* stores workout session logs as a `WorkoutLogBook` containing `WorkoutLog` entries.
* does not depend on the other three components: `UI`, `Logic`, or `Storage` (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components).

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/ay2526s2-cs2103-f08-1a/tp/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save PowerRoster data (client data, workout logs, and user preferences) in JSON format, and read them back into corresponding objects.
* inherits from `AddressBookStorage`, `WorkoutLogBookStorage`, and `UserPrefStorage`, which means it can be treated as any one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### View command and client detail panel

The `view` feature is implemented as a collaboration between `Logic` and `UI`:

1. `AddressBookParser` routes `view INDEX` to `ViewCommandParser`.
1. `ViewCommandParser` parses the index into a `ViewCommand`.
1. `ViewCommand` validates the index against `Model#getFilteredPersonList()` and returns a `CommandResult` that includes the target `Person`.
1. `MainWindow#executeCommand` checks `CommandResult#isShowPersonView()` and forwards the `Person` to `PersonDetailPanel#displayPerson`.

`PersonDetailPanel` has two states:

* Placeholder state shown when no client is currently being viewed.
* Detailed state shown after a successful `view INDEX` command.

To keep the panel consistent with model updates, `MainWindow` updates the panel after every command and clears the panel after successful commands when the currently viewed client no longer exists (e.g., after `delete` or `clear`).

### Sort feature

The sort feature allows users to sort the displayed client list by various attributes in ascending or descending order while preserving JavaFX reactivity.

The sort mechanism is facilitated by three main components:
* `SortCommand` stores attribute name and order, reconstructs the `Comparator<Person>` during execution.
* `SortCommandParser` validates that exactly one attribute is specified and that order is valid (`asc`/`desc`).
* `PersonComparators` centralizes comparison logic for supported `Person` attributes.

The sequence diagram below illustrates interactions when the user executes `sort n/ o/asc`:

<puml src="diagrams/SortSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `sort n/ o/asc` command" />

`ModelManager` exposes a `SortedList<Person>` wrapped around the filtered list, so filtering and sorting compose cleanly. `list` command resets the filter predicate and does not reset the active comparator.

Two attributes have special handling for absent values: clients with no location set always sort to the end of the list in ascending order (and to the top in descending), and clients with no rate set are treated as having the lowest rate and sort first in ascending order (and last in descending).

### Profile field update pattern (note/plan/status/rate/measure)

Several commands share a common implementation pattern:

1. Parse and validate command-specific prefixes.
1. Resolve target client from `Model#getFilteredPersonList()` using the provided index.
1. Construct an updated `Person` using the `with*()` methods (e.g., `withStatus()`, `withRate()`), which are backed by `Person.Builder` and copy all unaffected fields automatically.
1. Persist the update through `Model#setPerson(...)`.

This design keeps command behavior predictable and avoids hidden side effects between fields.

The commands differ mainly in their field-level semantics:

* `note`: supports replace (`n/`) and append (`a/`) modes. Exactly one mode must be provided.
* `plan`: accepts only predefined programme categories and supports explicit clear using empty value.
* `status`: supports only `active` or `inactive`. Duplicate status prefixes are rejected.
* `rate`: supports non-negative monetary values (up to 2 decimal places) and explicit clear.
* `measure`: supports partial updates across `h/`, `w/`, and `bf/`, each with range checks and clear semantics.

Storage and migration behavior for these fields is intentionally explicit:

* `status` supports backward compatibility by defaulting missing legacy values to `active` during JSON loading.
* `rate`, `plan`, and measurement fields are required in persisted JSON and validated on load.

### Help feature

`HelpCommand` delegates all command-word-to-usage lookups to `CommandRegistry` — a single utility class that maintains an ordered `LinkedHashMap` of every command word to its `MESSAGE_USAGE` string.

When `help` is executed without arguments, `HelpCommand` iterates `CommandRegistry` to produce a combined usage string and returns a `CommandResult` with `showHelp=true`, which causes `MainWindow` to open the help window. When a specific command word is provided, only that command's usage string is returned inline — no window is opened. The command word matching is case-insensitive.

If an unknown command word is provided, `HelpCommand` returns an informational message indicating the command is unrecognised and suggests running `help` without arguments.

**Extensibility:** Adding help support for a new command requires only one change — registering the new command in `CommandRegistry`. No other class needs updating.

The sequence diagram below illustrates interactions when the user executes `help` (bare, no arguments). When a specific command word is provided (e.g. `help add`), the flow is identical except `CommandResult` is created with `showHelp=false` and no window is opened.

<puml src="diagrams/HelpSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `help` Command" />

### Workout log feature (`log` and `last`)

Workout logging is the most domain-specific behavior in PowerRoster because it links client records to a separate `WorkoutLogBook` entity.

#### Key behavior

* `log INDEX [time/TIME] [l/LOCATION]` writes a new `WorkoutLog` entry keyed by client ID.
* If `time/` is omitted, current system time is used.
* If `l/` is omitted, client default location is reused; if that is empty, the stored log location remains empty and is displayed as `N/A` by retrieval flows.
* Duplicate logs are rejected.
* `last INDEX` reads the latest log for the client and reports a no-log message when none exists.

#### Activity flow

<puml src="diagrams/WorkoutLogActivityDiagram.puml" alt="Activity flow for logging and retrieving workout sessions" />

This flow captures the key decision points (`index` validity, fallback defaults, duplicate detection, and retrieval behavior) while omitting low-level parser and collection details.

### Future enhancements

Potential future enhancements include undo/redo support, archival workflows for old client records/workout logs and expansion of workout logs and plans to include more information (e.g., type of exercise, number of sets and reps, etc.).


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**: Freelance personal fitness trainers

* Fully freelance, not affiliated with any single gym which means he/she manages his/her own client base independently
* Handles a diverse client base of 10-25 clients, with varying fitness goals, workout plans and gym location
* Prefers laptop apps for work and keyboard-driven workflows over Graphical User Interface (GUI) navigation
* Currently, has client information spread out across different applications, which makes it time-consuming to retrieve and update client information, and needs a *centralised* *application* to help with this
* Needs to access/update clients' information before/after a session

**Value proposition**: PowerRoster helps freelance personal fitness trainers manage diverse client needs by linking their workout histories, plans, gym locations, etc. directly to their contact profiles. This allows for a *centralised* *application* for trainers to efficiently access any information needed about a client via an easy-to-use application optimised for text commands.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

#### Implemented in current version

| Priority | As a …​       | I want to …​                                      | So that I can…​                                                                 |
|----------|---------------|--------------------------------------------------|---------------------------------------------------------------------------------|
| `* * *`  | trainer       | list all clients in my roster                    | get an overview of my entire client base                                       |
| `* * *`  | trainer       | add a client and his/her information             |                                                                                 |
| `* * *`  | trainer       | delete a client’s contact                        | keep my client *roster* neat and up-to-date                                    |
| `* * *`  | trainer       | attach free-form notes to a client's profile     | record observations or other details to note from past sessions and remind myself for future sessions |
| `* * *`  | trainer       | tag a client to a specific gym location          | identify which venue I am training them at without clarifying each time        |
| `* *`    | trainer       | view a client's complete profile in a separate view | get a full separate picture of the client and their needs without the interference of other client information displayed |
| `* *`    | new user      | read about the available commands and their usage | learn how to use the application and refer to the instructions when I forget a certain command |
| `* *`    | trainer       | search for a client by name                      | retrieve their full profile instantly without scrolling through the entire list of clients |
| `* *`    | trainer       | filter clients by gym location                   | plan and schedule my clients better to ensure that my travel route is efficient |
| `* *`    | trainer       | record injuries, medical conditions or physical limitations for each client | assign appropriate and safe exercises, and avoid aggravating existing conditions |
| `* *`    | trainer       | assign a *workout programme* or routine to a client | track what programme they are currently supposed to follow, separate from individual session logs |
| `* *`    | trainer       | update a client's contact details                | ensure their details remain accurate over time                                 |
| `* *`    | trainer       | create *workout session logs* for each client    | have a way to track their training history where necessary |
| `* *`    | trainer       | see the last session date for each client        | identify clients I have not seen recently and decide whether to follow up      |
| `* *`    | trainer       | mark a client as active or inactive              | focus on current clients while retaining records of past ones for future reference |
| `* *`    | trainer       | add body measurements for each client (weight, body fat %, etc.) | track their physical progress quantitatively over time                         |
| `* *`    | trainer       | store a *session rate* for each client           | recall their pricing quickly when preparing invoices                           |
| `* *`    | trainer       | group clients together under a shared label      | track clients that are part of batch or group training sessions and contact them easily |
| `* *`    | trainer       | sort my client list by different attributes (e.g. name, location, date of birth) | organise my view depending on the task that I seek to do                       |
| `* *`    | trainer       | export or back up my client data                 | do not lose critical client information if something goes wrong                |

#### Yet to be implemented (near-future and beyond)

| Priority | As a …​       | I want to …​                                      | So that I can…​                                                                 |
|----------|---------------|--------------------------------------------------|---------------------------------------------------------------------------------|
| `* *`    | trainer       | record a client’s diet                           | identify which diet a client is currently adopting without clarifying each time |
| `* *`    | trainer       | record a client's dietary restrictions           | account for nutritional needs when designing their fitness programme           |
| `* *`    | trainer       | set specific fitness goals for each client       | measure whether they are on track to meet their objectives                     |
| `*`      | trainer       | record emergency contact information for each client | act quickly to inform relevant contacts in the event of a *health emergency* during training |
| `*`      | trainer       | see a summary of my total *active client* count and key details | monitor my *workload* and decide whether I have capacity to take on new clients |
| `*`      | trainer       | record payment status for each payment cycle     | follow up on outstanding payments without losing track                         |
| `*`      | trainer       | visualise a client's progress through charts     | identify trends in their performance at a glance and adjust their programme accordingly |
| `*`      | trainer       | store reusable workout templates                 | refer to my *workout programmes* in one place and efficiently assign tried-and-tested programmes to new or similar clients |
| `*`      | trainer       | filter or search clients by other specific attributes (e.g. dietary restriction, injury, *workout programme*) | quickly identify all clients sharing a particular condition or requirement     |

### Use cases

(For all use cases below, the **System** is the `PowerRoster` and the **Actor** is the `trainer`, unless specified otherwise)

To keep this section focused on non-trivial interactions, only unique interaction patterns are documented in full detail. Simpler commands with identical interaction patterns (e.g., straightforward index lookup + field update) are covered by representative use cases and noted as variations.

**Use case: UC01 - Add a client**
**Preconditions:** Trainer has launched PowerRoster.
**Guarantees:** A new client is added to the *roster* if all required fields are valid.

**MSS**

1. Trainer requests to add a new client with respective details.
2. PowerRoster validates the details.
3. PowerRoster creates and stores the *client profile* in the *roster*.
4. PowerRoster confirms the successful addition.

   Use case ends.

**Extensions**

* 2a. PowerRoster detects that one or more required fields are missing.
    * 2a1. PowerRoster informs the Trainer of the missing fields.
    * 2a2. Trainer re-enters the details.

      Use case resumes from step 2.
* 2b. PowerRoster detects that the provided details contain invalid values.
    * 2b1. PowerRoster informs the Trainer of the invalid fields and corresponding accepted values.
    * 2b2. Trainer re-enters the corrected details.

      Use case resumes from step 2.
* 2c. PowerRoster detects that a duplicate client already exists.
    * 2c1. PowerRoster informs the Trainer of the duplicate and inability to add a new client.

      Use case ends.

**Use case: UC02 - Edit a client**
**Preconditions:** Trainer has launched PowerRoster. At least one client exists in the *roster*.
**Guarantees:** The selected fields of the client profile are updated if inputs are valid.

**MSS**

1. Trainer requests to edit a specific client and provides updated details.
2. PowerRoster locates the client.
3. PowerRoster validates the provided details.
4. PowerRoster updates the client profile.
5. PowerRoster confirms the successful update to the Trainer.

   Use case ends.

**Extensions**

* 2a. PowerRoster cannot find a client matching the given identifier.
    * 2a1. PowerRoster informs the Trainer that no matching client was found and no update was carried out.

      Use case ends.
* 3a. PowerRoster detects that the provided details contain invalid values.
    * 3a1. PowerRoster informs the Trainer of the invalid fields and corresponding accepted values.

      Use case ends.

**Use case: UC03 - Delete a client**
**Preconditions:** Trainer has launched PowerRoster. At least one client exists in the *roster*.
**Guarantees:** The client and all associated data are removed if deletion is successful.

**MSS**

1. Trainer requests to delete a specific client.
2. PowerRoster locates the client.
3. PowerRoster removes the client and all associated data from the *roster*.
4. PowerRoster confirms the successful deletion to the Trainer.

   Use case ends.

**Extensions**

* 2a. PowerRoster cannot find a client matching the given identifier.
    * 2a1. PowerRoster informs the Trainer that no matching contact was found and no deletion was carried out.

      Use case ends.

**Use case: UC04 - View Help and Command Guide**
**Actor:** New user
**Preconditions:** User has launched PowerRoster.
**Guarantees:** The requested command usage information is displayed.

**MSS**

1. User requests to view the help guide.
2. PowerRoster opens the help window and displays the list of available commands with their syntax and descriptions.

   Use case ends.

**Extensions**

* 1a. User requests help for a specific command.
    * 1a1. PowerRoster displays only the usage instructions for the specified command.

      Use case ends.
* 1b. User requests help for an unknown command.
    * 1b1. PowerRoster informs the User that the command is unknown.
    * 1b2. PowerRoster displays a message suggesting to view the full help guide instead.

      Use case ends.

**Use case: UC05 - Search and filter clients**
**Preconditions:** Trainer has launched PowerRoster.
**Guarantees:** Clients matching the query criteria are displayed.

**MSS**

1. Trainer requests to search or filter clients and provides one or more query terms.
2. PowerRoster retrieves and displays all matching clients.

   Use case ends.

**Extensions**

* 2a. No clients match the provided query criteria.
   * 2a1. PowerRoster informs the Trainer that no matching clients were found.

      Use case ends.
* 2b. The *roster* has no clients.
    * 2b1. PowerRoster informs the Trainer that there are no clients in the *roster*.

      Use case ends.

**Use case: UC06 - View a client's full profile**
**Preconditions:** Trainer has launched PowerRoster. At least one client exists in the *roster*.
**Guarantees:** Full details of the selected client are displayed.

**MSS**

1. Trainer requests to view a specific client profile.
2. PowerRoster locates the client.
3. PowerRoster displays the client's full details.

   Use case ends.

**Extensions**

* 2a. The specified identifier does not match any existing client.
    * 2a1. PowerRoster informs the Trainer that the identifier was invalid.

      Use case ends.

**Use case: UC07 - Update specialised client fields (note/status/rate/measure/plan)**
**Preconditions:** Trainer has launched PowerRoster. At least one client exists in the *roster*.
**Guarantees:** One or more specialized client fields are updated according to command-specific rules.

**MSS**

1. Trainer requests to update a specialised field for a specific client.
2. PowerRoster locates the client.
3. PowerRoster validates field-specific inputs and update mode.
4. PowerRoster applies the requested field update.
5. PowerRoster confirms the successful update to the Trainer.

   Use case ends.

**Extensions**

* 2a. The specified identifier does not match any existing client.
    * 2a1. PowerRoster informs the Trainer that the identifier was invalid.

      Use case ends.

* 3a. The command contains an invalid update mode or invalid value (e.g., unsupported plan category, invalid status, invalid measurement, invalid rate format).
    * 3a1. PowerRoster informs the Trainer of the relevant validation error and accepted values.

      Use case ends.

* 3b. An update is requested in which no changes are required (e.g., status already equals requested value, append with empty note).
    * 3b1. PowerRoster informs the Trainer that no effective change was made.

      Use case ends.

**Use case: UC08 - Log a workout session**
**Preconditions:** Trainer has launched PowerRoster. At least one client exists in the *roster*.
**Guarantees:** A new workout session log is recorded for the selected client.

**MSS**

1. Trainer requests to log a workout session for a specific client.
2. PowerRoster locates the client and validates the provided session details.
3. PowerRoster records the workout session log entry.
4. PowerRoster confirms the successful log creation.

   Use case ends.

**Extensions**

* 2a. The specified identifier does not match any existing client.
    * 2a1. PowerRoster informs the Trainer that the identifier was invalid.

      Use case ends.
* 2b. PowerRoster detects that the provided details contain invalid values.
    * 2b1. PowerRoster informs the Trainer of the validation error and the accepted values.

      Use case ends.
* 3a. A duplicate workout log is detected.
    * 3a1. PowerRoster informs the Trainer that an identical log already exists and no new log was created.

      Use case ends.

**Use case: UC09 - View most recent workout session**
**Preconditions:** Trainer has launched PowerRoster. At least one client exists in the *roster*.
**Guarantees:** The most recent workout session details for the selected client are displayed, when available.

**MSS**

1. Trainer requests to retrieve the most recent session of a specific client.
2. PowerRoster locates the client.
3. PowerRoster retrieves and displays the latest workout session details for that client.

   Use case ends.

**Extensions**

* 2a. The specified identifier does not match any existing client.
    * 2a1. PowerRoster informs the Trainer that the identifier was invalid.

      Use case ends.
* 3a. No workout session logs exist for the client.
    * 3a1. PowerRoster informs the Trainer that no previous session exists.

      Use case ends.

**Use case: UC10 - Sort clients**
**Preconditions:** Trainer has launched PowerRoster. At least one client exists in the *roster*.
**Guarantees:** The client list is sorted according to the specified sorting criteria.

**MSS**

1. Trainer requests to sort the client list using a specified sorting criterion.
2. PowerRoster validates the sorting request.
3. PowerRoster sorts and displays the updated client list.

   Use case ends.

**Extensions**

* 2a. The sorting request is incomplete or incorrect.
    * 2a1. PowerRoster informs the Trainer that the request format is invalid and shows the expected format.

      Use case ends.
* 2b. The sorting request contains an unsupported sorting criterion.
    * 2b1. PowerRoster informs the Trainer that the sorting criterion is invalid and no sorting is performed.

      Use case ends.
* 2c. The sorting request contains duplicate prefixes (e.g., `sort n/ n/`).
    * 2c1. PowerRoster informs the Trainer that duplicate prefixes are not allowed.

      Use case ends.

**Use case: UC11 - Clear all clients and workout logs**
**Preconditions:** Trainer has launched PowerRoster.
**Guarantees:** All clients and workout logs are removed from storage and in-memory state.

**MSS**

1. Trainer requests to clear all application data.
2. PowerRoster removes all clients and workout logs.
3. PowerRoster confirms that all data has been cleared.

   Use case ends.

### Non-Functional Requirements

1. Should work on any *mainstream OS* as long as it has Java `17` or above installed.
2. Should be able to hold up to 1000 clients without noticeable sluggishness for core operations (e.g., list/find/filter/sort), even though the typical trainer stores 10-25 clients.
3. All functions provided in PowerRoster should be able to be carried out via the Command Line Interface (CLI) only.  
4. All client data should be stored in a single file and automatically saved after every successful command that alters the data stored to allow for easy backups and transfer to other devices if needed.
5. A user with above-average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
6. Should provide a helpful error message every time an invalid command is entered.
7. Should ensure basic data validation for all user-entered fields to prevent logically invalid values (e.g., negative session rate).
8. The application is not required to carry out any Internet communication for any of its functionality.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS  
* **Centralised application:** A single application consolidating all client-related information into one place, eliminating the need for the Trainer to switch between multiple applications (e.g. notes apps, spreadsheets, messaging apps) to retrieve or add client data.  
* **Roster:** The complete list of all clients stored in PowerRoster.  
* **Client Profile:** A record within PowerRoster storing all information associated with a specific client (e.g. contact details, gym location, workout history, dietary needs).  
* **Workout Session Log:** A recorded entry of a completed training session for a client, including details such as date, duration and exercises performed.
* **Workout Programme:** A structured plan of exercises assigned to a client to follow over a period of time (e.g. Push, Pull and Legs).
* **Active Client**: A client currently receiving training sessions from the Trainer.
* **Session Rate:** The fee charged by the Trainer per training session for a specific client.
* **Health emergency:** An unexpected medical situation that occurs during a training session that requires medical attention.
* **Workload:** The total number of active clients managed and trained by a Trainer currently.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy it into an empty folder.

   1. Double-click the jar file. Note that on some systems, double-clicking the jar file would not run it. In that case, `cd` into the folder you put the jar file in, and use the `java -jar PowerRoster.jar` command to run the application.<br>
      Expected: The GUI appears with sample clients loaded. The initial window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
      Expected: The most recent window size and location are retained.

1. Exiting from command line

   1. Prerequisites: App is running.

   1. Test case: `exit`<br>
      Expected: App shuts down gracefully.

### Help and command guidance

1. Showing help for all commands

   1. Test case: `help`<br>
      Expected: Full help content is shown.

1. Showing help for a specific command

   1. Test case: `help add`<br>
      Expected: Help content for the add command is shown.

1. Invalid command help request

   1. Test case: `help unknowncommand`<br>
      Expected: Unknown-command help message is shown.

### Add, view, edit, and delete clients

1. Adding clients

   1. Prerequisites: Start from a clean list using `clear` if needed.

   1. Test case: `add n/Alex Tan g/M dob/01/01/1995 p/91234567 e/alex.tan@example.com a/123 Clementi Ave 3 l/Clementi ActiveSG t/beginner`<br>
      Expected: Client is added successfully and shown in the list.

   1. Test case: Re-run the same `add` command above<br>
      Expected: Duplicate-client error is shown; no new client is added.

1. Listing all clients

   1. Prerequisites: At least one client exists.

   1. Test case: `list`<br>
      Expected: All clients are displayed.

1. Viewing a client's full profile

   1. Prerequisites: Run `list`. Multiple clients are shown.

   1. Test case: `view 1`<br>
      Expected: The 1st client's full profile is shown in the detail panel, with a success message in the result display.

   1. Test case: `view 0`<br>
      Expected: No profile is shown or updated. Error details are shown.

   1. Other incorrect view commands to try: `view`, `view x`, `view ...` (where index is larger than the list size)<br>
      Expected: Similar to previous.

1. Editing clients

   1. Prerequisites: At least one client exists.

   1. Test case: `edit 1 p/98765432 e/alex.updated@example.com`<br>
      Expected: Selected fields are updated successfully.

   1. Test case: `edit 1 dob/31/02/1995`<br>
      Expected: Validation error is shown for invalid date.

1. Deleting a client while all clients are shown

   1. Prerequisites: Run `list`. Multiple clients are shown.

   1. Test case: `delete 1`<br>
      Expected: First client is deleted from the list. Details of the deleted client are shown in the result message.

   1. Test case: `delete 0`<br>
      Expected: No client is deleted. Error details are shown.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. Detail panel consistency after delete

   1. Prerequisites: `view 1` has been executed successfully and the profile is visible.

   1. Test case: `delete 1`<br>
      Expected: Deleted client is removed from the list and the detail panel resets to placeholder if that deleted client was being viewed.

### Find, filter, and sort clients

1. Find

   1. Prerequisites: At least two clients exist with different names.

   1. Test case: `find alex`<br>
      Expected: Only matching clients are shown.

   1. Test case: `find ALEx`<br>
      Expected: Same matching results as `find alex` (case-insensitive matching).

1. Filter

   1. Prerequisites: At least one client has a location and at least one client has no specified location.

   1. Test case: `filter l/Clementi`<br>
      Expected: Only clients with matching location phrases are shown.

   1. Test case: `filter l/Clementi l/Jurong`<br>
      Expected: Clients matching either location phrase are shown.

   1. Test case: `filter l/`<br>
      Expected: Only clients with no specified location are shown.

   1. Test case: `filter l/Clementi l/`<br>
      Expected: Validation error is shown because mixed non-empty and empty location phrases are not allowed in one command.

1. Sort

   1. Prerequisites: At least two clients exist.

   1. Test case: `sort n/ o/desc`<br>
      Expected: Displayed list is sorted by name in descending order.

   1. Test case: `sort n/`<br>
      Expected: Displayed list is sorted by name in ascending order (default order).

   1. Test case: `sort x/`<br>
      Expected: Invalid sorting-criterion error is shown.

### Notes, plans, status, rate, and measurements

1. Notes

   1. Prerequisites: At least one client exists.

   1. Test case: `note 1 n/Prefers evening sessions.`<br>
      Expected: Existing note is replaced with the new note.

   1. Test case: `note 1 a/Monitor knee stability.`<br>
      Expected: New note text is appended to the existing note.

   1. Test case: `note 1 n/Prefers morning sessions. a/Track hydration.`<br>
      Expected: Validation error is shown because add and append note modes cannot be used together.

1. Workout plan

   1. Prerequisites: At least one client exists.

   1. Test case: `plan 1 wp/FULL BODY`<br>
      Expected: Programme is assigned successfully.

   1. Test case: `plan 1 wp/`<br>
      Expected: Programme is cleared successfully.

   1. Test case: `plan 1 wp/UNKNOWN`<br>
      Expected: Validation error is shown for unsupported plan category.

1. Status

   1. Prerequisites: At least one client exists.

   1. Test case: `status 1 s/inactive`<br>
      Expected: Status is updated to inactive.

   1. After the previous test case, test case: `status 1 s/inactive`<br>
      Expected: Message indicates no change because status is already inactive.

1. Rate

   1. Prerequisites: At least one client exists.

   1. Test case: `rate 1 r/120.5`<br>
      Expected: Rate is set successfully.

   1. Test case: `rate 1 r/`<br>
      Expected: Rate is cleared successfully.

   1. Test case: `rate 1 r/120.555`<br>
      Expected: Validation error is shown for invalid rate value format.

1. Measurements

   1. Prerequisites: At least one client exists.

   1. Test case: `measure 1 h/175.0 w/70.5 bf/14.0`<br>
      Expected: Measurement fields are updated successfully.

   1. Test case: `measure 1 h/ w/ bf/`<br>
      Expected: All specified fields are cleared successfully.

   1. Test case: `measure 1 h/20.0`<br>
      Expected: Validation error is shown for height out of acceptable range.

### Logging and retrieving workout sessions

1. Logging a session with defaults

   1. Prerequisites: At least one client exists.

   1. Test case: `log 1`<br>
      Expected: A new log entry is created for client 1 using current time and the client's saved location (or `N/A` if none).

1. Logging with explicit values

   1. Prerequisites: At least one client exists.

   1. Test case: `log 1 time/26/03/2026 14:18 l/Sengkang ActiveSG Gym`<br>
      Expected: A new log entry is created using the provided time and location.

1. Invalid log input

   1. Prerequisites: At least one client exists.

   1. Test case: `log 0`<br>
      Expected: Invalid-index error is shown; no log is added.

   1. Test case: `log 1 time/26/03/2070 14:18`<br>
      Expected: Validation error is shown for invalid future time.

1. Retrieving the most recent session

   1. Prerequisites: At least two clients exist.

   1. Test case: Execute `log 1`, then execute `last 1`<br>
      Expected: Most recent session time and location for client 1 are shown.

   1. Test case: `last 0`<br>
      Expected: Invalid-index error is shown.

   1. Test case: `last 2` (for a client with no workout logs)<br>
      Expected: Message indicates no previous session exists for that client.

### Clearing all entries

1. Clearing clients and workout logs

   1. Prerequisites: At least one client exists and at least one workout log exists.

   1. Test case: `clear`<br>
      Expected: All clients and workout logs are removed.

### Saving data

1. Dealing with missing/corrupted data files

   1. Missing file test:
      1. Close the app.
      1. Delete `data/addressbook.json` (and optionally `data/workoutlogbook.json`).
      1. Re-launch the app.
      Expected: App starts with sample/empty data and recreates required data files.

   1. Corrupted file test:
      1. Close the app.
      1. Edit `data/addressbook.json` and introduce invalid JSON (e.g., remove a closing bracket).
      1. Re-launch the app.
      Expected: App handles read failure gracefully and initializes fallback data; an error is logged.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Effort**

This project started from AddressBook-Level3 and evolved into a personal fitness trainer-centric roster management application.

### Scope and difficulty

Compared with baseline AB3, the project required additional effort in three main areas:

* **Domain adaptation**: Adapting AB3's generic contact model to support a trainer workflow while preserving the architecture quality that was already present in AB3, along with command clarity and consistency. The AB3 mainly dealt with one entity (Person) but to support our target user, we had to add in another entity (WorkoutLog) which increased the difficulty of this project.
* **Feature expansion**: Adding and integrating useful commands and fields (e.g., session rate, body measurements, workout plan, workout logs) across logic, model, storage, and UI. Our team also put in much effort in designing and enhancing the UI to better suit our target user.
* **Validation and UX consistency**: Ensuring robust validation rules, clear error handling, and predictable behavior especially when combining operations such as find, sort then view.

### Key implementation challenges

* **Cross-component changes**: Many features required synchronized updates across parser, command, model entities, JSON adapters, and UI rendering.
* **Backward compatibility of stored data**: New fields required careful handling during loading to avoid breaking existing data files.
* **Keeping documentation aligned with implementation**: As command semantics evolved, use cases, requirements, and manual testing steps needed updates to remain accurate.

### Effort distribution (high-level)

* **Core feature implementation and integration**: Largest share of effort for all members, with features split evenly amongst everyone.
* **Testing and bug fixing**: Significant effort due to validation edge cases and the inclusion of clear error messages depending on each scenario.
* **Documentation and diagrams**: Average effort to maintain implementation-accurate descriptions and clarity.

### Reuse and its impact

* The project reuses the **AB3 architecture and project scaffold** as its foundation. This was particularly helpful when the team was implementing new fields and commands which followed a similar workflow as existing commands such as add and edit.
* Reuse reduced setup and boilerplate effort, allowing the team to focus effort on domain-specific behavior which included adding additional fields, commands and other features.
* Third-party libraries (e.g., JavaFX, Jackson, JUnit) were used as standard infrastructure (brought over from AB3). The main effort remained in integrating them into product-specific logic and constraints.

### Conclusion
Overall, our team has worked together well to achieve the current release of PowerRoster v1.5, everyone has put in a sufficiently large amount of effort to ensure that the product has minimal bugs and suits the target user and value proposition we set out at the start of this project.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned Enhancements**

Team size: 5

1. **Make client-name validation more inclusive for legal name patterns:** The current name validation rejects some commonly used legal-name fragments (e.g., `s/o`, `d/o`) because of slash handling in command parsing. Although we have stated this in the user guide and provided the workaround for users, we plan to look into adjusting name parsing/validation so these patterns can be entered safely while preserving command-prefix parsing reliability.

2. **Extend workout log viewing from only latest-session to session-history view:** Currently, trainers can only retrieve the most recent session for a client. We plan to add a history-view flow that shows multiple past workout entries in reverse chronological order so trainers can review progression across sessions, not just retrieve the most recent session.

3. **Expand workout log and workout plan detail granularity:** Current workout logs and assigned plans are intentionally compact. We plan to support richer structured details (e.g., exercise type, set count, repetition count) to improve training traceability and give the trainer access to more information for planning, tracking, etc. while keeping input constraints clear.

4. **Add explicit currency support for client session rates:** The current rate field stores numeric values without an explicit currency label and it is up to the user to be aware of the currency type of the values they are storing. We plan to allow trainers to specify a currency code for rate values and display it consistently in command feedback and UI.
