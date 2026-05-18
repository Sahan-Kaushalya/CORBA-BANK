# CORBA Bank Application

This is a simple Banking application demonstrating the use of Common Object Request Broker Architecture (CORBA) with Java.

## Project Structure
This is a standard Java Maven project.

### IDL Definition
The core of the application is defined in the `src/main/java/lk/kaushalya/bcd/Banking.idl` file, which specifies the `Account` interface:

```idl
module Banking{
    interface Account{
        string getBankName(in string accountNo);
        void deposit(in string accountNo, in double amount);
        void withdraw(in string accountNo, in double amount) raises (InsufficientBalance);
        double getBalance( in string accountNo);
    };
};
```

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

1.  **Compile the IDL.** From the `src/main/java/lk/kaushalya/bcd` directory, run the `idlj` tool to generate the Java stubs and skeletons.
    ```bash
    # Navigate to the directory containing the IDL file
    cd src/main/java/lk/kaushalya/bcd

    # Run the IDL compiler
    idlj -fall Banking.idl
    ```

2.  **Build with Maven.** Navigate back to the project root and build with Maven.
    ```bash
    # Go back to the project root
    cd ../../../../..

    # Build the project
    mvn clean install
    ```

## Running the Application

*(Note: Provide specific instructions here depending on your Main class names)*

1.  Start the `orbd` (Object Request Broker Daemon) service from your terminal:
    ```bash
    orbd -ORBInitialPort 1050 -ORBInitialHost localhost
    ```

2.  In a **new terminal**, start the CORBA Server.

3.  In another **new terminal**, run the CORBA Client to interact with the bank account.
