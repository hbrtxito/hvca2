package com.company;
import java.util.*;
/**
 * Created by hbrtxito on 12/1/2015.
 */
public class ResolvedTicket extends Ticket {
    private Date ResolutionDate;
    private String Resolution;

    public Date getResolutionDate() {
        return ResolutionDate;
    }

    public String getResolution() {
        return Resolution;
    }

    public ResolvedTicket(String desc, int p, String rep, Date date, Date resolutionDate, String resolution) {
        super(desc, p, rep, date);
        this.ResolutionDate = resolutionDate;
        this.Resolution = resolution;
    }

    public String toString(){
        //super(desc, );
        return("ID= " + this.ticketID + " Issued: " + this.getDescription() + " Priority: " +
                this.getPriority() + " Reported by: "
                + this.getReporter() + " Reported on: " + this.getDateReported()
                + "Resolution: " + this.getResolution() + "Resolution Date: "+ this.getResolutionDate() );
    }

}




