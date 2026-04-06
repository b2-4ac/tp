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

To keep the panel consistent with model updates, `MainWindow` updates the panel after every command and also clears the panel after successful commands when the currently viewed client no longer exists (e.g., after a `delete` or `clear`).

### Sort feature

#### Implementation

The sort feature allows users to sort the client list by various attributes in ascending or descending order. It uses JavaFX's `SortedList` wrapper to maintain reactivity with the UI.

The sort mechanism is facilitated by three main components:
* `SortCommand` - Stores attribute name and order, reconstructs the `Comparator<Person>` during execution
* `SortCommandParser` - Parses user input using a map-based approach to validate attributes and order
* `PersonComparators` - Utility class that centralizes all comparison logic for `Person` attributes

The sequence diagram below illustrates the interactions within the `Logic` component when the user executes `sort n/ o/asc`:

<puml src="diagrams/SortSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `sort n/ o/asc` Command" />

The `ModelManager` wraps the `FilteredList` with a `SortedList`, allowing sorting and filtering to work together. When `SortCommand.execute()` is called, it retrieves the appropriate comparator from `PersonComparators` and updates the model's comparator. The UI's `ListView` automatically updates through JavaFX's observable pattern.

Once set, the comparator remains active until replaced by another `sort` command. Commands such as `list` reset the filter predicate to show all clients, but do not reset the active comparator.

#### Design considerations:

**Aspect: Where to store comparator logic**

* **Alternative 1 (current choice):** Centralize in `PersonComparators` utility class.
  * Pros: Follows Single Responsibility Principle, easy to extend.
  * Cons: One additional class to maintain.

* **Alternative 2:** Keep comparators in `SortCommandParser`.
  * Pros: Fewer classes.
  * Cons: Mixes parsing and business logic, harder to test.

### Status Feature

The status feature allows trainers to mark clients as either active or inactive, enabling them to focus on current clients while retaining historical records.

#### Implementation

The status mechanism is implemented through the following components:

* `Status` — A class that represents a client's status, containing a nested `StatusEnum` with two values: `ACTIVE` and `INACTIVE`.
* `StatusCommand` — Executes the status change operation on a specified client.
* `StatusCommandParser` — Parses user input to create a `StatusCommand`.

The `Status` class enforces validation to ensure only valid status values ("active" or "inactive", case-insensitive) are accepted.

#### Key Design Decisions

**Storage and Migration:**
* New clients are automatically assigned `active` status when created via `AddCommand`.
* The `JsonAdaptedPerson` class handles backward compatibility by defaulting missing status fields to "active" when loading old data files.
* Status is persisted alongside other client fields in the JSON storage.

**Immutability:**
* Following the existing Person class design pattern, changing a client's status creates a new Person object with the updated status while preserving all other fields.
* This maintains data consistency and simplifies undo/redo operations if implemented in the future.

**Validation:**
* The `Status` class validates input using a regex pattern, rejecting invalid values like "pending" or "unknown".
* Duplicate status prefixes (e.g., `status 1 s/active s/inactive`) are detected and rejected by the parser with a user-friendly message: "Only one status value (either active or inactive) can be specified."
* If the client already has the specified status, the command does not modify the client record and instead returns an informational message.

### Rate Feature

The rate feature allows trainers to store a per-client session rate and update it via a dedicated command.

#### Implementation

The rate mechanism is implemented through the following components:

* `Rate` — A value class that represents a client's session rate.
* `RateCommand` — Replaces or clears the rate for a specified client.
* `RateCommandParser` — Parses user input to create a `RateCommand`.

The `Rate` class normalizes valid values to 2 decimal places (e.g., `120`, `120.`, and `.5` are stored as `120.00`, `120.00`, and `0.50` respectively).

#### Key Design Decisions

**Dedicated command for rate changes:**
* Rate updates are performed only through `rate INDEX r/RATE`.
* `EditCommand` intentionally preserves the existing rate to keep rate updates explicit and auditable.

**Storage and Migration:**
* `JsonAdaptedPerson` persists `rate` in the data file. For old data files without a `rate` field, the user/developer has to manually add it in the JSON file (e.g., `"rate": "120.00"`) to avoid errors when loading the data.

**Immutability:**
* Following the existing model pattern, updating a rate creates a new `Person` instance with only the `rate` field changed while preserving all other fields.

### Body Measurement Feature

The body measurement feature allows trainers to store and update a client's height, weight, and body fat percentage via a dedicated command.

#### Implementation

The measurement mechanism is implemented through the following components:

* `Height`, `Weight`, `BodyFatPercentage` - Value classes representing each measurement field.
* `MeasureCommand` - Replaces and/or clears measurements for a specified client.
* `MeasureCommandParser` - Parses user input to create a `MeasureCommand`.

The three value classes enforce numeric range and format constraints (up to 1 decimal place), while still allowing blank values for explicit clear operations. Inputs with trailing dots (e.g., `170.`) are accepted and normalized to 1 decimal place in storage.

In the UI detail panel, measurement values are displayed to 1 decimal place to match measurement precision.

#### Key Design Decisions

**Dedicated command for measurement changes:**
* Measurement updates are performed through `measure INDEX [h/...] [w/...] [bf/...]`.
* Omitted measurement prefixes preserve existing values.

**Clear semantics:**
* Providing a prefix with no value (`h/`, `w/`, or `bf/`) triggers a clear attempt for that specific field.
* Each targeted field reports either `cleared` or `already cleared` based on whether it previously had a value.
* Mixed outcomes are supported in one command (e.g., one field cleared while another field is updated).

**Immutability:**
* Following the existing model pattern, updating measurements creates a new `Person` instance with only the measurement fields changed while preserving all other fields.

**Storage and Migration:**
* `JsonAdaptedPerson` persists `height`, `weight`, and `bodyFatPercentage` in the data file and validates these values when converting to model objects.

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
| `* *`    | trainer       | record a client’s diet                           | identify which diet a client is currently adopting without clarifying each time |
| `* *`    | trainer       | record a client's dietary restrictions           | account for nutritional needs when designing their fitness programme           |
| `* *`    | trainer       | record injuries, medical conditions or physical limitations for each client | assign appropriate and safe exercises, and avoid aggravating existing conditions |
| `* *`    | trainer       | assign a *workout programme* or routine to a client | track what programme they are currently supposed to follow, separate from individual session logs |
| `* *`    | trainer       | update a client's contact details                | ensure their details remain accurate over time                                 |
| `* *`    | trainer       | create *workout session logs* for each client    | track their training history and refer to them to tailor future sessions accordingly |
| `* *`    | trainer       | see the last session date for each client        | identify clients I have not seen recently and decide whether to follow up      |
| `* *`    | trainer       | mark a client as active or inactive              | focus on current clients while retaining records of past ones for future reference |
| `* *`    | trainer       | add body measurements for each client (weight, body fat %, etc.) | track their physical progress quantitatively over time                         |
| `* *`    | trainer       | store a *session rate* for each client           | recall their pricing quickly when preparing invoices                           |
| `* *`    | trainer       | group clients together under a shared label      | track clients that are part of batch or group training sessions and contact them easily |
| `* *`    | trainer       | sort my client list by different attributes (e.g. name, location, last session date) | organise my view depending on the task that I seek to do                       |
| `* *`    | trainer       | set specific fitness goals for each client       | measure whether they are on track to meet their objectives                     |
| `* *`    | trainer       | export or back up my client data                 | do not lose critical client information if something goes wrong                |
| `*`      | trainer       | record emergency contact information for each client | act quickly to inform relevant contacts in the event of a *health emergency* during training |
| `*`      | trainer       | see a summary of my total *active client* count and key details | monitor my *workload* and decide whether I have capacity to take on new clients |
| `*`      | trainer       | record payment status for each payment cycle     | follow up on outstanding payments without losing track                         |
| `*`      | trainer       | visualise a client's progress through charts     | identify trends in their performance at a glance and adjust their programme accordingly |
| `*`      | trainer       | store reusable workout templates                 | refer to my *workout programmes* in one place and efficiently assign tried-and-tested programmes to new or similar clients |
| `*`      | trainer       | filter or search clients by other specific attributes (e.g. dietary restriction, injury, *workout programme*) | quickly identify all clients sharing a particular condition or requirement     |

### Use cases

(For all use cases below, the **System** is the `PowerRoster` and the **Actor** is the `trainer`, unless specified otherwise)

**Use case: UC01 - List all clients**
**Preconditions:** Trainer has launched PowerRoster.
**Guarantees:** The full client *roster* is displayed.

**MSS**

1. Trainer requests to list all clients.
2. PowerRoster retrieves and displays all clients.

   Use case ends.

**Use case: UC02 - Add a client**
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

**Use case: UC03 - Edit a client**
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

**Use case: UC04 - Delete a client**
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

**Use case: UC05 - View Help and Command Guide**
**Actor:** New user
**Preconditions:** User has launched PowerRoster.
**Guarantees:** The requested command usage information is displayed.

**MSS**

1. User requests to view the help guide.
2. PowerRoster displays the list of available commands with their syntax and descriptions.

   Use case ends.

**Extensions**

* 1a. User requests help for a specific command.
    * 1a1. PowerRoster displays only the usage instructions for the specified command.

      Use case ends.
* 1b. User requests help for an unknown command.
    * 1b1. PowerRoster informs the User that the command is unknown.
    * 1b2. PowerRoster displays a message suggesting to view the full help guide instead.

      Use case ends.

**Use case: UC06 - Search for a client by name**
**Preconditions:** Trainer has launched PowerRoster.
**Guarantees:** Clients whose names match the query are displayed.

**MSS**

1. Trainer requests to search for a client by name and provides one or more keywords.
2. PowerRoster retrieves and displays all matching clients.

   Use case ends.

**Extensions**

* 2a. No clients match the search query.
    * 2a1. PowerRoster informs the Trainer that no matching clients were found.

      Use case ends.
* 2b. The *roster* has no clients.
    * 2b1. PowerRoster informs the Trainer that there are no clients in the *roster*.

      Use case ends.

**Use case: UC07 - Filter clients by gym location**
**Preconditions:** Trainer has launched PowerRoster.
**Guarantees:** Clients whose gym locations match the given location phrase(s) are displayed.

**MSS**

1. Trainer requests to filter clients by gym location and provides one or more location phrases.
2. PowerRoster retrieves and displays all clients whose gym location matches at least one provided location phrase.
3. PowerRoster confirms the number of matching clients to the Trainer.

   Use case ends.

**Extensions**

* 1a. Trainer provides an invalid filter request format.
    * 1a1. PowerRoster informs the Trainer that the request format is invalid and shows the expected format.

      Use case ends.
* 1b. Trainer requests to filter clients with no specified location.
    * 1b1. PowerRoster displays clients with no specified location.

      Use case resumes from step 3.
* 2a. No clients' location match the filter criteria.
    * 2a1. PowerRoster informs the Trainer that no clients were found for the specified location criteria.

      Use case ends.

**Use case: UC08 - View a client's full profile**
**Preconditions:** Trainer has launched PowerRoster. At least one client is shown in the current list.
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

**Use case: UC09 - Add or append a note to a client**
**Preconditions:** Trainer has launched PowerRoster. At least one client exists in the *roster*.
**Guarantees:** The selected client's note is added, replaced, or appended according to the request.

**MSS**

1. Trainer requests to add or append a note to a specific client and provides the note content.
2. PowerRoster locates the client and applies the requested note update.
3. PowerRoster confirms the successful update to the Trainer.

   Use case ends.

**Extensions**

* 2a. The specified identifier does not match any existing client.
    * 2a1. PowerRoster informs the Trainer that the identifier was invalid.

      Use case ends.
* 2b. Trainer requests to add and append in the same request.
    * 2b1. PowerRoster informs the Trainer that both actions cannot be performed at the same time.

      Use case ends.
* 2c. Trainer requests to add a note but provides an empty note.
    * 2c1. PowerRoster replaces the existing note with an empty note.

      Use case ends.
* 2d. Trainer requests to append a note but provides an empty note.
    * 2d1. PowerRoster does not change the existing note.

      Use case ends.

**Use case: UC10 - Change a client's status**
**Preconditions:** Trainer has launched PowerRoster. At least one client exists in the *roster*.
**Guarantees:** The selected client's status is updated if the request is valid.

**MSS**

1. Trainer requests to change the status of a specific client.
2. PowerRoster locates the client.
3. PowerRoster validates the requested status value.
4. PowerRoster updates the client's status and confirms the change.

   Use case ends.

**Extensions**

* 2a. PowerRoster cannot find a client matching the given identifier.
    * 2a1. PowerRoster informs the Trainer that the identifier was invalid.

      Use case ends.
* 3a. The given status is invalid.
    * 3a1. PowerRoster shows an error message.

      Use case ends.
* 4a. The client already has the specified status.
    * 4a1. PowerRoster indicates that no changes were made.

      Use case ends.

**Use case: UC11 - Set or clear a client's session rate**
**Preconditions:** Trainer has launched PowerRoster. At least one client exists in the *roster*.
**Guarantees:** The selected client's session rate is set or cleared.

**MSS**

1. Trainer requests to set the rate of a specific client and provides a rate value.
2. PowerRoster locates the client and validates the rate.
3. PowerRoster sets the client's rate.
4. PowerRoster confirms the successful update to the Trainer.

   Use case ends.

**Extensions**

* 1a. Trainer requests to clear a client's rate.
    * 1a1. PowerRoster locates the client and clears the client's existing rate.
    * 1a2. PowerRoster confirms the successful update to the Trainer.

      Use case ends.
* 2a. The specified identifier does not match any existing client.
    * 2a1. PowerRoster informs the Trainer that the identifier was invalid.

      Use case ends.
* 2b. The rate value is invalid.
    * 2b1. PowerRoster informs the Trainer of the validation error and the accepted values.

      Use case ends.

**Use case: UC12 - Set or clear a client's body measurements**
**Preconditions:** Trainer has launched PowerRoster. At least one client exists in the *roster*.
**Guarantees:** The selected body measurement fields are updated.

**MSS**

1. Trainer requests to set one or more measurements of a specific client and provides valid values.
2. PowerRoster locates the client and validates the provided measurements.
3. PowerRoster updates the specified measurement fields.
4. PowerRoster confirms the successful update to the Trainer.

   Use case ends.

**Extensions**

* 1a. Trainer requests to clear one or more measurement fields.
    * 1a1. PowerRoster clears the corresponding measurement fields.
    * 1a2. PowerRoster confirms the successful update to the Trainer.

      Use case ends.
* 2a. The specified identifier does not match any existing client.
    * 2a1. PowerRoster informs the Trainer that the identifier was invalid.

      Use case ends.
* 2b. One or more measurement values are invalid.
    * 2b1. PowerRoster informs the Trainer of the validation error and the accepted values.

      Use case ends.

**Use case: UC13 - Assign or clear a client's workout programme**
**Preconditions:** Trainer has launched PowerRoster. At least one client exists in the *roster*.
**Guarantees:** The selected client's workout programme is assigned or cleared.

**MSS**

1. Trainer requests to assign a workout programme to a specific client and provides a valid programme category.
2. PowerRoster locates the client and validates the provided programme category.
3. PowerRoster updates the client's workout programme.
4. PowerRoster confirms the successful update to the Trainer.

   Use case ends.

**Extensions**

* 1a. Trainer requests to clear the client's workout programme.
    * 1a1. PowerRoster clears the client's workout programme.
    * 1a2. PowerRoster confirms the successful update to the Trainer.

      Use case ends.
* 2a. The specified identifier does not match any existing client.
    * 2a1. PowerRoster informs the Trainer that the identifier was invalid.

      Use case ends.
* 2b. The programme category is invalid.
    * 2b1. PowerRoster informs the Trainer of the validation error.

      Use case ends.

**Use case: UC14 - Log a workout session**
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

**Use case: UC15 - View most recent workout session**
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

**Use case: UC16 - Sort clients**
**Preconditions:** Trainer has launched PowerRoster. At least one client exists in the displayed list.
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

### Non-Functional Requirements

1. Should work on any *mainstream OS* as long as it has Java `17` or above installed.
2. Should be able to hold up to 1000 clients without noticeable sluggishness for core operations (e.g., list/find/filter/sort), even though the typical trainer stores 10-25 clients.
3. All functions provided in PowerRoster should be able to be carried out via the Command Line Interface (CLI) only.  
4. All client data should be stored in a single file and automatically saved after every successful command that alters the data stored to allow for easy backups and transfer to other devices if needed.
6. A user with above-average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
7. Should provide a helpful error message every time an invalid command is entered.
8. Should ensure basic data validation for all user-entered fields to prevent logically invalid values (e.g., negative session rate).
9. The application is not required to carry out any Internet communication for any of its functionality.

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

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file.<br>
      Expected: Shows the GUI with a set of sample clients. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. Exiting from command line

   1. Prerequisites: App is running.

   1. Test case: `exit`<br>
      Expected: App shuts down gracefully.

### Deleting a client

1. Deleting a client while all clients are being shown

   1. Prerequisites: List all clients using the `list` command. Multiple clients in the list.

   1. Test case: `delete 1`<br>
      Expected: First client is deleted from the list. Details of the deleted client shown in the result message.

   1. Test case: `delete 0`<br>
      Expected: No client is deleted. Error details shown in the result message.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

### Viewing a client's full profile

1. Viewing from the current list

   1. Prerequisites: List all clients using the `list` command. Multiple clients in the list.

   1. Test case: `view 1`<br>
      Expected: The 1st client's full profile is shown in the detail panel. Success message is shown in the result display.

   1. Test case: `view 0`<br>
      Expected: No profile is shown/changed. Error details shown in the status message.

   1. Other incorrect view commands to try: `view`, `view x`, `view ...` (where index is larger than the list size)<br>
      Expected: Similar to previous.

1. Detail panel consistency after delete

   1. Prerequisites: `view 1` has been executed successfully and the profile is visible.

   1. Test case: `delete 1`<br>
      Expected: Deleted client is removed from the list and the detail panel resets to placeholder if the deleted client was the one being viewed.

### Logging and retrieving workout sessions

1. Logging a session with defaults

   1. Prerequisites: Multiple clients in list; at least one client has a location.

   1. Test case: `log 1`<br>
      Expected: A new log entry is created for client 1 using current time and the client's saved location (or `N/A` if none).

1. Logging with explicit values

   1. Test case: `log 1 time/26/03/2026 14:18 l/Sengkang ActiveSG Gym`<br>
      Expected: A new log entry is created using the provided time and location.

1. Invalid log input

   1. Test case: `log 0`<br>
      Expected: Invalid index error shown; no log added.

   1. Test case: `log 1 time/26/03/2070 14:18`<br>
      Expected: Validation error shown for invalid future time.

1. Retrieving most recent session

   1. Prerequisites: At least one `log` command has been executed successfully for client 1.

   1. Test case: `last 1`<br>
      Expected: Most recent session time and location for client 1 are shown.

   1. Test case: `last 0`<br>
      Expected: Invalid index error shown.

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
