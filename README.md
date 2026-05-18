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
* Java Development Kit (JDK) 8
* Apache Maven

## Building the Project

1. Compile the IDL file to generate the required Java stubs and skeletons using the `idlj` tool provided by the JDK. (Depending on the exact build setup, this might be handled by a Maven plugin or done manually).

   Example manual compilation (from the directory containing the IDL file):
   ```bash
   idlj -fall Banking.idl
   ```

2. Build the project using Maven:
   ```bash
   mvn clean install
   ```

## Running the Application

*(Note: Provide specific instructions here depending on your Main class names)*

1. Start the `orbd` (Object Request Broker Daemon) service:
   ```bash
   orbd -ORBInitialPort 1050 -ORBInitialHost localhost
   ```

2. Start the CORBA Server.

3. Run the CORBA Client to interact with the bank account.
