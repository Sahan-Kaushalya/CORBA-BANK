# CORBA Bank Application

This is a simple Banking application demonstrating the use of Common Object Request Broker Architecture (CORBA) with Java.

## Project Structure
This is a standard Java Maven project.

### IDL Definition
The core of the application is defined in the `src/main/java/Banking.idl` file, which specifies the `Account` interface:

```idl
module Banking{

    exception InsufficientBalance{
        string message;
    };
    
    interface Account{
        string getBankName(in string accountNo);
        void deposit(in string accountNo, in double amount);
        void withdraw(in string accountNo, in double amount) raises (InsufficientBalance);
        double getBalance( in string accountNo);
    };
};
```

### Generated CORBA Files
When you compile the `Banking.idl` file, it generates several Java classes in the `src/main/java/Banking` package to handle the CORBA communication. These include:

*   **`Account.java`**: The Java interface mapping for the IDL `Account` interface.
*   **`AccountOperations.java`**: The interface containing the methods defined in the IDL (`getBankName`, `deposit`, `withdraw`, `getBalance`).
*   **`AccountPOA.java`**: The Portable Object Adapter (POA) skeleton class that your server implementation must extend.
*   **`_AccountStub.java`**: The stub class used by the client to communicate with the server.
*   **`AccountHelper.java` & `AccountHolder.java`**: Helper and holder classes used for type casting and passing parameters in CORBA.
*   **`InsufficientBalance.java`**: The Java exception class generated from the IDL `raises (InsufficientBalance)` declaration.
*   **`InsufficientBalanceHelper.java` & `InsufficientBalanceHolder.java`**: Helper and holder classes for the exception.

### Server Implementation
The server side is implemented in `src/main/java/lk/kaushalya/bcd/server/`:
*   **`AccountImpl.java`**: This class extends `AccountPOA` and provides the actual logic for the banking operations:
    *   Maintains an in-memory hash map (`db`) of account numbers and their balances.
    *   Provides initial dummy data (e.g., account "001123" has LKR 1000.0).
    *   Resolves bank names based on the first 3 digits of the account number (e.g., "001" is Bank of Ceylon).
    *   Handles deposit and withdrawal logic, including throwing an `InsufficientBalance` exception if a withdrawal cannot be completed.
*   **`BankServer.java`**: The main class that initializes the ORB (Object Request Broker), registers the `AccountImpl` object with the Naming Service under the name `BankAccount`, and waits for incoming client requests.

### Client Implementation
The client side is implemented in `src/main/java/lk/kaushalya/bcd/client/ATMClient.java`. 
It provides an interactive console application that connects to the `BankAccount` object registered in the CORBA Naming Service. 

When you run the client, it will display a welcome banner and prompt you for an account ID (e.g., `001123`, `002123`). After entering a valid account ID, you are presented with a menu:

```text
=======================================================
                CORBA Banking System                    
=======================================================


Enter Your Account ID : 
001123


===========================================================================
1. Get Bank Name | 2. Deposit | 3. Withdraw | 4. Get Balance | 5. Exit
===========================================================================
```

You can then input a number (1-5) to interact with the server:
1.  **Get Bank Name**: Retrieves the bank name associated with the account prefix.
2.  **Deposit**: Prompts for an amount to add to the account balance.
3.  **Withdraw**: Prompts for an amount to remove from the account balance (may throw an error if the balance is too low).
4.  **Get Balance**: Displays the current balance in LKR.
5.  **Exit**: Closes the ATM client.

## Basic Terminal Commands
If you are new to using the terminal or command prompt, here are a few essential commands you will need:

*   **`pwd`** (Print Working Directory): Shows the full path of the directory you are currently in.
*   **`ls`** (List): Lists the files and folders in your current directory. Use `ls -l` for a more detailed view. On Windows PowerShell, `ls` or `dir` works.
*   **`cd <directory_name>`** (Change Directory): Moves you into a different folder.
    *   Example: `cd src` moves you into the `src` folder.
    *   Example: `cd ..` moves you up one level to the parent folder.

## Prerequisites
* **Java Development Kit (JDK) 8**. Newer versions of Java have removed the CORBA modules.
* Apache Maven

## Setting up the Java Environment
Before building or running the application, you must ensure you are using JDK 8.

### Windows (PowerShell Example)
Verify your current Java version:
```powershell
java -version
```
If it's not Java 8, you can set it for your current terminal session. **Replace the path with your actual JDK 8 installation path.**
```powershell
# Prepend the JDK 8 bin directory to your PATH
$env:path="C:\Program Files\Java\jdk1.8.0_202\bin;$env:path"

# Verify the change
java -version
javac -version
```

### Linux / macOS (Bash Example)
Verify your current Java version:
```bash
java -version
```
If it's not Java 8, you can set it for your current terminal session. **Replace the path with your actual JDK 8 installation path.**
```bash
# Set JAVA_HOME and update the PATH
export JAVA_HOME=/path/to/your/jdk1.8.0
export PATH=$JAVA_HOME/bin:$PATH

# Verify the change
java -version
javac -version
```

## Building the Project

1.  **Compile the IDL.** From the `src/main/java` directory, run the `idlj` tool to generate the Java stubs and skeletons.
    ```bash
    # Navigate to the directory containing the IDL file
    cd src/main/java

    # List the files to ensure you see Banking.idl
    ls

    # Run the IDL compiler
    idlj -fall Banking.idl
    ```
    *(Note: This step generates the files in the `Banking` package mentioned above.)*

2.  **Build with Maven.** Navigate back to the project root and build with Maven.
    ```bash
    # Go back to the project root
    cd ../../..

    # Build the project
    mvn clean install
    ```

## Running the Application

1.  **Start the `orbd`** (Object Request Broker Daemon) service from your terminal. This is required for the Naming Service.
    ```bash
    orbd -ORBInitialPort 1050 -ORBInitialHost localhost
    ```

2.  **Start the CORBA Server.** Open a **new terminal**, navigate to your project root (or compiled classes directory), and run the server. (Make sure your CLASSPATH is set correctly, or use Maven `exec:java` if configured).
    ```bash
    # Example using java command directly from compiled classes
    # java -cp target/classes lk.kaushalya.bcd.server.BankServer -ORBInitialPort 1050 -ORBInitialHost localhost
    ```

3.  **Run the CORBA Client.** Open another **new terminal** and run your client class to interact with the bank account.
    ```bash
    # Example using java command directly from compiled classes
    # java -cp target/classes lk.kaushalya.bcd.client.ATMClient -ORBInitialPort 1050 -ORBInitialHost localhost
    ```