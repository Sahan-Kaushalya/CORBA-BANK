package lk.kaushalya.bcd.server;

import Banking.Account;
import Banking.AccountHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class BankServer {
    public static void main(String[] args) {
        ORB orb = ORB.init(args, null);
        try{
            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();

            AccountImpl account = new AccountImpl();
            org.omg.CORBA.Object object = rootPOA.servant_to_reference(account);

            Account accountRef = AccountHelper.narrow(object);

            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            String name = "BankAccount";
            NameComponent path[] = ncRef.to_name(name);
            ncRef.rebind(path, accountRef);

            System.out.println("\n=======================================================");
            System.out.println("                CORBA Banking System                    ");
            System.out.println("=======================================================");
            System.out.println("\nServer Log: Bank Server is ready and waiting for client requests...");

            orb.run();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
