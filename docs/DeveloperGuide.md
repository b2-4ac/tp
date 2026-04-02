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
Github Copilot was used by our team for its auto-complete features during our developement of this application

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well }_

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

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
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

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g. `CommandBox`, `ResultDisplay`, `PersonListPanel`, `PersonDetailPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.
* renders a split layout where `PersonListPanel` shows a compact summary list while `PersonDetailPanel` displays full details for a selected client via the `view` command.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

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
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.
    - `CommandResult` can optionally carry a `Person` to be displayed in the UI detail panel. This is used by commands such as `view` that trigger a UI detail-view update without modifying model data.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* stores the workout logs in an `ArrayList` of `WorkoutLog` objects
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save address book data, workout log book data and user preference data in JSON format, and read them back into corresponding objects.
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

To keep the panel consistent with model updates, `MainWindow` also clears the detail panel after successful commands when the currently viewed person no longer exists (e.g., after a `delete` or `clear`).

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
* Duplicate status prefixes (e.g., `status 1 s/active s/inactive`) are detected and rejected by the parser.

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

The three value classes enforce numeric range and format constraints (up to 1 decimal place), while still allowing blank values for explicit clear operations.

#### Key Design Decisions

**Dedicated command for measurement changes:**
* Measurement updates are performed through `measure INDEX [h/...] [w/...] [bf/...]`.
* Omitted measurement prefixes preserve existing values.

**Clear semantics:**
* Providing a prefix with no value clears that specific measurement field.
* `MeasureCommand` returns a clear-success message when all provided measurement fields are blank.

**Immutability:**
* Following the existing model pattern, updating measurements creates a new `Person` instance with only the measurement fields changed while preserving all other fields.

**Storage and Migration:**
* `JsonAdaptedPerson` persists `height`, `weight`, and `bodyFatPercentage` in the data file and validates these values when converting to model objects.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


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

**Target user profile**: Freelance Personal Fitness Trainers

* Fully freelance, not affiliated with any single gym which means he/she manages his/her own client base independently
* Handles a diverse client base of 10-25 clients, with varying fitness goals, dietary requirements, workout plans and gym location
* Prefers laptop apps for work and keyboard-driven workflows over Graphical User Interface (GUI) navigation
* Currently, has client information spread out across different applications, which makes it time-consuming to retrieve and update client information, and needs a *centralised* *application* to help with this
* Needs to pull up/update a specific client’s full information before/after a session

**Value proposition**: PowerRoster helps freelance personal fitness trainers manage diverse client needs by linking their workout histories, dietary restrictions and preferred locations directly to their contact profiles. This allows for a *centralised* *application* for trainers to efficiently access any information needed about a client via an easy-to-use application optimised for text commands.


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

**Use case: UC01 \- List all clients**  
**Preconditions: Trainer launched PowerRoster.**  
**Guarantees: The full client *roster* (if any) is displayed.**

**MSS**

1. Trainer requests to list all clients.  
2. PowerRoster retrieves and displays all clients in the *roster*.

   Use case ends.

**Use case: UC02 \- Add a client**  
**Preconditions: Trainer has launched PowerRoster.**   
**Guarantees: A new client is added to the *roster* if all the required details are valid.**

**MSS**

1. Trainer requests to add a new client and input the respective details.  
2. PowerRoster validates the details.  
3. PowerRoster creates a new *client profile* and adds it to the *roster*.  
4. PowerRoster confirms the successful addition to the Trainer.

   Use case ends.

**Extensions**

* 2a. PowerRoster detects that one or more required fields are missing.

    * 2a1. PowerRoster informs the Trainer of the missing fields.  
    * 2a2. Trainer re-enters the details with the missing fields included.

      Steps 2a1-2a2 are repeated until all required fields are present.

      Use case resumes from step 2.
* 2b. PowerRoster detects that the provided details contain invalid values (e.g. invalid phone number format).  
    
    * 2b1. PowerRoster informs the Trainer of the invalid fields and the expected format.  
    * 2b2. Trainer re-enters the corrected details.

      Steps 2b1-2b2 are repeated until all fields are valid.

      Use case resumes from step 2.
* 2c. PowerRoster detects that a client with the same name already exists.  
  
    * 2c1. PowerRoster warns the Trainer of the potential duplicate.  
    * 2c2. Trainer confirms they wish to proceed with adding the client.

      Use case resumes from step 3.
* 2d. Trainer optionally provides a gym location.  

    * 2d1. PowerRoster validates the gym location.  
        * 2d1a1. PowerRoster informs the Trainer of the invalid input and the expected format.  
        * 2d1a2. Trainer re-enters the location.
      
          Use case resumes from step 2d1.
    * 2d2. PowerRoster saves the gym location to the new client’s profile.  

* 2e. Trainer optionally provides a note for the client.  
    
    * 2e1. PowerRoster saves the note to the client’s profile. 
  
      Use case resumes from step 3.

**Use case: UC03 \- Delete a client**  
**Preconditions: Trainer has launched PowerRoster. At least one client exists in the *roster*.**   
**Guarantees: The client and all associated data are permanently removed if the deletion is confirmed.**

**MSS**

1. Trainer requests to delete a specific client.
2. PowerRoster locates the client.
3. PowerRoster removes the client and all associated data from the *roster*.
4. PowerRoster confirms the successful deletion to the Trainer.

**Extensions**

* 2a. PowerRoster cannot find a client matching the given identifier.
    * 2a1. PowerRoster informs the Trainer that no matching contact was found and no deletion was carried out.  
      
      Use case ends.

**Use case: UC04 \- View Help and Command Guide**  
**Actor: New user**  
**Preconditions: User has launched PowerRoster.**  
**Guarantees: The requested command usage information is displayed.**

**MSS:**

1. User requests to view the help guide.
2. PowerRoster displays the list of all available commands with their syntax and descriptions.

   Use case ends.

**Extensions:**

* 1a. User requests help for a specific command.
    * 1a1. PowerRoster displays only the usage instructions for the specified command.

      Use case ends.

* 1b. User requests help for an unknown command.
    * 1b1. PowerRoster informs the User that the command is unknown.
    * 1b2. PowerRoster displays a message suggesting to type 'help' to see all available commands.

      Use case ends.

**Use case: UC05 \- Search for a Client by Name**  
**Preconditions: Trainer has launched PowerRoster.**  
**Guarantees: All clients whose names match the search query are displayed.**  
**MSS:**

1. Trainer requests to search for a client by name and provides the name to search for.
2. PowerRoster retrieves and displays all clients whose names match the search query.

   Use case ends.

**Extensions:**

* 2a. No clients match the search query.
    * 2a1. PowerRoster informs the Trainer that no matching clients were found.
      
      Use case ends.
* 2b. The *roster* has no clients 

    * 2b1. PowerRoster informs the Trainer that there are no clients in the *roster*.  
        
      Use case ends.

**Use case: UC06 \- Filter Clients by Gym Location**
**Preconditions: Trainer has launched PowerRoster. At least one client in the *roster* has a gym location specified.**
**Guarantees: All clients who train at the specified gym location are displayed.**
**MSS:**
1. Trainer requests to filter clients by gym location and provides one or more location phrases.
2. PowerRoster retrieves and displays all clients whose gym location matches at least one provided location phrase.
3. PowerRoster confirms the number of clients found for the specified gym location to the Trainer.

   Use case ends.

**Extensions:**
* 1a. Trainer omits the required command prefix for filtering by gym location.
    * 1a1. PowerRoster informs the Trainer that the command format is invalid and shows the expected command format.  
      
      Use case ends.
* 1b. Trainer provides a blank location value for filtering.
    * 1b1. PowerRoster displays clients with no specified location.

      Use case resumes from step 3.
*2a.  No clients match the filter criteria.
    * 2a1. PowerRoster informs the Trainer that no clients were found for the specified gym location.

      Use case ends.
* 1c. Trainer provides multiple location values for filtering, with at least one blank value.
    * 1c1. PowerRoster informs the Trainer that the command format is invalid.

      Use case ends.

**Use case: UC07 \- View a Client's Full Profile**  
**Preconditions: Trainer has launched PowerRoster. At least one client is shown in the current list.**

**MSS:**

1. Trainer requests to view a specific client profile by index.
2. PowerRoster locates the client.
3. PowerRoster displays that client's full details in the detail panel.

    Use case ends.

**Extensions:**

* 2a. The specified identifier does not match any existing client.
    * 2a1. PowerRoster informs the Trainer that the identifier was invalid.

   Use case ends.

**Use case: UC08 \- Add/Append a Note to a Client**  
**Preconditions: Trainer has launched PowerRoster.**

**MSS:**

1. Trainer requests to add or append a note to a specific client and provides the note.
2. PowerRoster locates the client and adds or appends the note to the client profile.
3. PowerRoster confirms the successful update to the Trainer.

   Use case ends.

**Extensions:**

* 2a. The specified identifier does not match any existing client.
    * 2a1. PowerRoster informs the Trainer that the identifier was invalid.

      Use case ends.
* 2b. PowerRoster detects that the client has requested to add and append at the same time.

    * 2b1. PowerRoster informs the Trainer that it is not possible to add and append at the same time.
        
      Use case ends.
* 2c. Trainer requests to add and provides an empty note.

    * 2c1. PowerRoster replaces the existing note with the empty note.
        
      Use case ends.
* 2d. Trainer requests to append and provides an empty note.

    * 2d1. PowerRoster does not change the existing note.

      Use case ends.
* 2e. Trainer requests to add or append more than one note at a time.

    * 2e1. PowerRoster informs the Trainer that it is not possible to do so.

      Use case ends.

**Use case: UC08 \- Change a Client's Status**
**Preconditions: Trainer has launched PowerRoster. At least one client exists in the displayed list.**

**MSS**
    
1. Trainer requests to list all clients or performs a search/filter.
2. PowerRoster shows a list of clients.
3. Trainer requests to change the status of a specific client by providing the index and new status (active/inactive).
4. PowerRoster updates the client's status and confirms the change.

   Use case ends.

**Extensions**

* 2a. The list is empty.

      Use case ends.

* 3a. The given index is invalid.
    * 3a1. PowerRoster shows an error message.

      Use case ends.

* 3b. The given status is invalid (not "active" or "inactive").
    * 3b1. PowerRoster shows an error message.

      Use case ends.

* 3c. The client already has the specified status.
    * 3c1. PowerRoster indicates that no changes were made.

      Use case ends.

**Use case: UC09 \- Set/Clear a Client's Session Rate**
**Preconditions: Trainer has launched PowerRoster. At least one client exists in the displayed list.**

**MSS**

1. Trainer requests to set the rate of a specific client and provides the rate.
2. PowerRoster locates the client and validates the rate.
3. PowerRoster sets the client's rate and confirms the successful update to the Trainer.

   Use case ends.

**Extensions**

* 2a. The specified identifier does not match any existing client.
    * 2a1. PowerRoster informs the Trainer that the identifier was invalid.

      Use case ends.

* 2b. The rate is invalid
    * 2b1. PowerRoster informs the user of the error.

      Use case ends.

* 1c. Trainer requests to clear a client's rate.
    * 1c1. PowerRoster locates the client and clears the client's existing rate.
    * 1c2. PowerRoster confirms the successful update to the Trainer.

      Use case ends.

**Use case: UC10 \- Set/Clear a Client's Body Measurements**
**Preconditions: Trainer has launched PowerRoster. At least one client exists in the displayed list.**

**MSS**

1. Trainer requests to set one or more measurements of a specific client and provides valid values.
2. PowerRoster locates the client and validates the provided measurements.
3. PowerRoster updates the specified measurement fields.
4. PowerRoster confirms the successful update to the Trainer.

   Use case ends.

**Extensions**

* 2a. The specified identifier does not match any existing client.
    * 2a1. PowerRoster informs the Trainer that the identifier was invalid.

      Use case ends.

* 2b. One or more measurement values are invalid.
    * 2b1. PowerRoster informs the Trainer of the validation error.

      Use case ends.

* 1c. Trainer requests to clear one or more measurement fields by passing empty prefixed values.
    * 1c1. PowerRoster clears the corresponding measurement fields.
    * 1c2. PowerRoster confirms the successful update to the Trainer.

      Use case ends.

**Use case: UC11 \- Assign/Clear a Client's Workout Programme**
**Preconditions: Trainer has launched PowerRoster. At least one client exists in the displayed list.**

**MSS**

1. Trainer requests to assign a workout programme to a specific client and provides a valid programme category.
2. PowerRoster locates the client and validates the provided programme category.
3. PowerRoster updates the client's workout programme.
4. PowerRoster confirms the successful update to the Trainer.

   Use case ends.

**Extensions**

* 2a. The specified identifier does not match any existing client.
    * 2a1. PowerRoster informs the Trainer that the identifier was invalid.

      Use case ends.

* 2b. The programme category is invalid.
    * 2b1. PowerRoster informs the Trainer of the validation error.

      Use case ends.

* 1c. Trainer requests to clear the client's workout programme by passing an empty prefixed value.
    * 1c1. PowerRoster clears the client's workout programme.
    * 1c2. PowerRoster confirms the successful update to the Trainer.

      Use case ends.

### Non-Functional Requirements

1. Should work on any *mainstream OS* as long as it has Java `17` or above installed.
2. Should be able to hold up to 1000 clients without a noticeable sluggishness at its peak performance, even though the typical trainer is expected to store 10-25 clients.  
3. All functions provided in PowerRoster should be able to be carried out via the Command Line Interface (CLI) only.  
4. Must automatically save data after every successful command that alters the data stored to prevent data loss if the device’s battery dies or the app is closed abruptly.  
5. All client data should be stored in a single file to allow for easy backups and transfer to other devices if needed.
6. A user with above-average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
7. Should provide a helpful error message every time an invalid command is entered.
8. Should ensure basic data validation for all data entries to prevent logical impossibilities (e.g., negative *session rate*)
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

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a client

1. Deleting a client while all clients are being shown

   1. Prerequisites: List all clients using the `list` command. Multiple clients in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No client is deleted. Error details shown in the status message. Status bar remains the same.

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

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
