import java.util.NoSuchElementException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;

class atm
{
    public static Scanner s = new Scanner(System.in);
    private static String number,pin;
    private static String path = "/home/steven/Desktop/atm/atm/info.txt";
    private static String path2 = "/home/steven/Desktop/atm/atm/aux.txt";
    public static int balance;

    public static void main(String[] args) throws FileNotFoundException, IOException
    {        
        while(true)
        {
            if(!getInfo()) continue;
            else break;       
        } 
            
        enterAccount();
    }

    private static boolean getInfo() throws FileNotFoundException
    {

        System.out.println("Enter your account number: (Enter 'register' if you don't have one)");
        String input = s.nextLine();
        if(input.equalsIgnoreCase("register"))
        {
            System.out.print("Enter a new account number: ");
            number = s.nextLine();
            System.out.print("Enter a new PIN code: ");
            pin = s.nextLine();
            try
            {
                int intPin = Integer.parseInt(pin);
            }
            catch(Exception e)
            {
                System.out.println("Error. Please try again.\n");
                return false;
            }

            try(FileWriter fileWriter = new FileWriter(path))
            {  
                fileWriter.write(number + "\n" + pin);
            }
            catch (IOException e)
            {
                System.out.println("Error.\n");
            }
            System.out.print("\033[H\033[2J");  
        }
        else
        {
            try(Scanner r = new Scanner(new FileReader(path)))
            {
                String aNum;
                try
                {
                    aNum = r.nextLine();
                }
                catch(NoSuchElementException e)
                {
                    System.out.println("You haven't got an account!");
                    return false;
                }
                if(input.equals(aNum))
                {
                    System.out.println("Enter the PIN code of your account: ");
                    String iPin = s.nextLine();
                    try
                    {
                        int iiPin = Integer.parseInt(iPin);
                    }
                    catch(Exception e)
                    {
                        System.out.println("Error. Please try again.\n");
                        return false;
                    }
                    try(Scanner o = new Scanner(new FileReader(path)))
                    {
                        String aux = o.nextLine();
                        String aPin = o.nextLine();
                        if(iPin.equals(aPin))
                        {
                            return true;
                        }
                        else
                        {
                            System.out.println("PIN code is incorrect");
                            return false;
                        }
                    }
                    catch(IOException e)
                    {
                        System.out.println("Error. Please try again.\n");
                        return false;        
                    }
                }
                else
                {
                    System.out.println("Account number is not correct. Try again.");
                }
            }
            catch(IOException e)
            {
                System.out.println("Error. Please try again.\n");
                return false;
            }
        }

        return false;
    }

    private static void enterAccount() throws FileNotFoundException, IOException
    {
        System.out.println("Entering account...");
        System.out.println("Entered account!");
        if(getBalance() >= 0) System.out.println("Balance: £" + balance);
        else System.out.println("Could not print balance");
        while(true)
            prompt();
    }

    private static void prompt() throws FileNotFoundException, IOException
    {
        System.out.println("Enter your command. (Type 'help' for a list of commands.)");
        String cmd = s.nextLine();
        if(cmd.equalsIgnoreCase("exit"))
            System.exit(0);
        else if(cmd.equalsIgnoreCase("deposit"))
            deposit();    
        else if(cmd.equalsIgnoreCase("balance"))
            System.out.println(balance);    
    }

    public static int getBalance() throws FileNotFoundException
    {
        try(Scanner a = new Scanner(new FileReader(path)))
        {
            String aux = a.nextLine();
            String aux2 = a.nextLine();
            try
            {
                balance = Integer.parseInt(a.nextLine());
            }
            catch(NoSuchElementException e)
            {
                try(PrintWriter fileWriter = new PrintWriter(new FileOutputStream(new File(path),true)))
                {  
                    balance = 0;
                    fileWriter.append("\n0");
                }
                catch(IOException ioe)
                {
                    System.out.println("Error. Please try again.\n");
                    return -1;
                } 
            }
        }
        catch(IOException ioe)
        {
            System.out.println("Error. Please try again.");
            return -1;
        }

        return balance;
    }

    public static void deposit() throws FileNotFoundException, IOException
    {
        File info = new File(path);
        File aux = new File(path2);
        System.out.println("Enter the amount of money that you want to deposit: ");
        int deposit;
        try
        {
            deposit = s.nextInt();
        }
        catch (InputMismatchException e)
        {
            System.out.println("Error. Please try again.");
            return;
        }

        PrintWriter pw = new PrintWriter(new FileOutputStream(aux,true));
        PrintWriter pw2 = new PrintWriter(new FileOutputStream(info, true));
        BufferedReader br = new BufferedReader(new FileReader(path));
        BufferedReader br2 = new BufferedReader(new FileReader(path2));

        String line = br.readLine();
        String line2;

        while(line != null)
        {
            try
            {
                if(balance != Integer.parseInt(line))
                    pw.append(line + "\n");  
                else
                    break;      
            }
            catch (NumberFormatException e)
            {
                pw.append(line + "\n");  
            }
            line = br.readLine();
        }

        balance += deposit;
        pw.append(Integer.toString(balance));

        info.delete();
        aux.renameTo(info);

        line2 = br2.readLine();

        while(line2 != null)
        {
            pw2.append(line2 + "\n");
            line2 = br2.readLine();
        }
                
        br.close();
        br2.close();
        pw.close();
        pw2.close();
    }
}
