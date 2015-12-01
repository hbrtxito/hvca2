package com.company;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class Main {

    public static void main(String[] args) {
	// write your code here

                LinkedList<Ticket> ticketQueue = new LinkedList<Ticket>();

                LinkedList<Ticket> SearchListTickets = new LinkedList<>(); // list of the tickets that were searched

                LinkedList <ResolvedTicket> resolvedTickets = new LinkedList<>(); //

                Scanner scan = new Scanner(System.in);

                while(true){

                    System.out.println("1. Enter Ticket\n2. Delete by ID\n3. Delete by Issue\n4.Search by Name\n5. Display All Tickets\n6. Quit ");
                    int task = Integer.parseInt(scan.nextLine());

                    if (task == 1) {
                        //Call addTickets, which will let us enter any number of new tickets
                        addTickets(ticketQueue);

                    } else if (task == 2) {
                        //delete a ticket by ID
                        deleteTicket(ticketQueue, resolvedTickets);

                    }else if (task == 3){
                        //delete by Issue
                        //deleteTicket();
                        deleteTicket_Issue(ticketQueue,SearchListTickets, resolvedTickets);

                    }

                    else if ( task == 4 ) {
                        //Search by name
                        SearchByName(ticketQueue,SearchListTickets);

                    }

                    else if ( task == 5 ) {
                        //Quit. Future prototype may want to save all tickets to a file
                        System.out.println("Quitting program");


                        break;
                    }
                    else if (task == 6){
                        To_File(ticketQueue);
                        System.exit(0);
                    }
                    else {
                        //this will happen for 3 or any other selection that is a valid int
                        //TODO Program crashes if you enter anything else - please fix
                        //Default will be print all tickets

                    }
                }

                scan.close();

            }
            public static int isValid_deleteID (String delete){
                int deleteID  =0 ;
                try {
                    deleteID =  Integer.parseInt(delete) ;

                }catch (NumberFormatException nfe){
                    System.out.println("The format of your ID is incorrect!");
                }

                return deleteID ;
            }


            public static Date isValid_Date (String date){
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                Date newDate = null;
                try {
                    newDate =  df.parse(date);

                }catch (ParseException psex){
                    System.out.println("The format of the date is incorrect!");
                }

                return newDate;
            }


            //Move the adding ticket code to a method
            protected static void addTickets(LinkedList<Ticket> ticketQueue) {
                Scanner sc = new Scanner(System.in);

                boolean moreProblems = true;
                String description;
                String reporter;
                //let's assume all tickets are created today, for testing. We can change this later if needed
                Date dateReported = new Date(); //Default constructor creates date with current date/time
                int priority;

                while (moreProblems){
                    System.out.println("Enter problem");
                    description = sc.nextLine();
                    System.out.println("Who reported this issue?");
                    reporter = sc.nextLine();
                    System.out.println("Enter priority of " + description);

                    priority = Integer.parseInt(sc.nextLine());

                    Ticket t = new Ticket(description, priority, reporter, dateReported);
                    //ticketQueue.add(t);

                    addTicketInPriorityOrder(ticketQueue, t);
                    //To test, let's print out all of the currently stored tickets
                    printAllTickets(ticketQueue);


                    System.out.println("More tickets to add?");
                    String more = sc.nextLine();
                    if (more.equalsIgnoreCase("N")) {
                        moreProblems = false;
                    }
                }

            }
            protected static void printAllTickets(LinkedList<Ticket> tickets) {
                System.out.println(" ------- All open tickets ----------");

                for (Ticket t : tickets ) {
                    System.out.println(t); //Write a toString method in Ticket class
                    //println will try to call toString on its argument

                }

                System.out.println(" ------- End of ticket list ----------");


            }
    protected  static  void To_File (LinkedList<Ticket> tickets){
        for (Ticket t : tickets ) {
            System.out.println(t); //Write a toString method in Ticket class
            //println will try to call toString on its argument

            try {

                String content = t.toString();

                File file = new File("src/opentickets.txt");

                // if file doesnt exists, then create it
                if (!file.exists()) {
                    file.createNewFile();
                }

                FileWriter fw = new FileWriter(file,true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(content + "\n");
                bw.close();

                //System.out.println("Done");

            } catch (IOException e) {
                e.printStackTrace();
            }



        }

    }
            protected  static  void deleteTicket_Issue (LinkedList<Ticket> ticketQueue, LinkedList <Ticket> SearchListTicket,LinkedList<ResolvedTicket> resolvedTickets ) {
                Scanner getanswer  = new Scanner(System.in);
                String answer ;
                //printAllTickets(ticketQueue);//display all tickets
                //Calling the method to search by name

                SearchByName(ticketQueue, SearchListTicket);

                System.out.println("Do you want to delete a ticket by its ID ?");
                answer = getanswer.nextLine();
                answer =answer.toUpperCase();


                if (ticketQueue.size() == 0) {    //no tickets!
                    System.out.println("No tickets to delete!\n");
                    return;
                }
                else if (answer.equals("Y")){
                    deleteTicket(SearchListTicket,resolvedTickets );
                }
                else {
                    Scanner deleteScanner = new Scanner(System.in);
                    System.out.println("Enter Issue of ticket to delete");
                    /*** checking***/
                    String delete_issue = deleteScanner.nextLine();


                    //Loop over all tickets. Delete the one with this ticket ID
                    boolean found = false;
                    for (Ticket ticket : ticketQueue) {
                        String issue_desc = ticket.getDescription();

                        if (issue_desc.contains(delete_issue)) {
                            found = true;
                            ticketQueue.remove(ticket);
                            System.out.println("Ticket with issue" + delete_issue + "was remove");
                            break; //don't need loop any more.
                        }
                    }
                    if (found == false) {
                        System.out.println("Ticket ID not found, no ticket deleted");
                        System.out.println(" Please enter the ID ticket again");
                        deleteTicket_Issue(ticketQueue, SearchListTicket, resolvedTickets);


                        //TODO – re-write this method to ask for ID again if not found
                    }
                    printAllTickets(ticketQueue);  //print updated list

                }

            }

            protected static void deleteTicket(LinkedList<Ticket> ticketQueue ,LinkedList<ResolvedTicket> resolvedTickets ) {
                printAllTickets(ticketQueue);   //display list for user


                if (ticketQueue.size() == 0) {    //no tickets!
                    System.out.println("No tickets to delete!\n");
                    return;
                }

                Scanner deleteScanner = new Scanner(System.in);
                System.out.println("Enter ID of ticket to resolve");
                /*** checking***/
                String delete = deleteScanner.nextLine();

                int deleteID =isValid_deleteID(delete);





                //Loop over all tickets. Delete the one with this ticket ID
                boolean found = false;
                for (Ticket ticket : ticketQueue) {
                    if (ticket.getTicketID() == deleteID) {
                        found = true;
                        System.out.println("Enter the resolution for this ticket");
                        String resolution = deleteScanner.nextLine();

                        System.out.println("Enter the resolution DATE for this ticket");
                        String resolutionString = deleteScanner.nextLine();
                        Date resolutionDate = isValid_Date(resolutionString);

                        ResolvedTicket resolved = new ResolvedTicket(ticket.getDescription() , ticket.getPriority() ,
                                ticket.getReporter(), ticket.getDateReported(), resolutionDate, resolution);

                        resolvedTickets.add(resolved);
                        System.out.println(resolved);
                        ticketQueue.remove(ticket);
                        System.out.println(String.format("Ticket %d deleted", deleteID));
                        break; //don't need loop any more.
                    }
                }
                if (found == false) {
                    System.out.println("Ticket ID not found, no ticket deleted");
                    System.out.println(" Please enter the ID ticket again");
                    deleteTicket(ticketQueue, resolvedTickets);


                    //TODO – re-write this method to ask for ID again if not found
                }
                printAllTickets(ticketQueue);  //print updated list



            }

            //Method - Search by name

            protected  static  void SearchByName (LinkedList<Ticket> ticketQueue , LinkedList <Ticket> SearchListTicket) {
                // printAllTickets(ticketQueue);//display all tickets

                if (ticketQueue.size() == 0) {    //no tickets!
                    System.out.println("No tickets to search!\n");
                    return;
                }

                Scanner SearchTicket = new Scanner(System.in);
                System.out.println("Enter the ticket description: ");
                /*** checking***/
                String Search = SearchTicket.nextLine();


                //Loop over all tickets. Delete the one with this ticket ID
                boolean found = false;

                System.out.println(" here are your tickets ");
                for (Ticket ticket : ticketQueue) {
                    String issue_desc=ticket.getDescription();

                    if (issue_desc.contains(Search)) {
                        SearchListTicket.add(ticket);
                        found = true;
                        //TicketQueue.remove(ticket);
                        System.out.println(ticket);
                        //System.out.println( );
                        //break; //don't need loop any more.
                    }
                }
                if (found == false) {
                    System.out.println("Ticket not found ");
                    //System.out.println(" Please enter the ID ticket again");
                    //deleteTicket_Issue(ticketQueue);


                    //TODO – re-write this method to ask for ID again if not found
                }
                // printAllTickets(ticketQueue);  //print updated list



            }


            protected static void addTicketInPriorityOrder(LinkedList<Ticket> tickets, Ticket newTicket){

                //Logic: assume the list is either empty or sorted

                if (tickets.size() == 0 ) {//Special case - if list is empty, add ticket and return
                    tickets.add(newTicket);
                    return;
                }

                //Tickets with the HIGHEST priority number go at the front of the list. (e.g. 5=server on fire)
                //Tickets with the LOWEST value of their priority number (so the lowest priority) go at the end

                int newTicketPriority = newTicket.getPriority();

                for (int x = 0; x < tickets.size() ; x++) {    //use a regular for loop so we know which element we are looking at

                    //if newTicket is higher or equal priority than the this element, add it in front of this one, and return
                    if (newTicketPriority >= tickets.get(x).getPriority()) {
                        tickets.add(x, newTicket);
                        return;
                    }
                }

                //Will only get here if the ticket is not added in the loop
                //If that happens, it must be lower priority than all other tickets. So, add to the end.
                tickets.addLast(newTicket);
            }



}
