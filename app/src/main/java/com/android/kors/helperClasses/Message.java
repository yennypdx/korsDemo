package com.android.kors.helperClasses;

public class Message {

    private static String CompletedAcceptText;
    static {
        CompletedAcceptText = "Well done! Your completion request has been approved. Onto the next one, my minions!! [kors.]";
    }

    public static String getCompletedAcceptText() {
        return CompletedAcceptText;
    }

    private static String CompletedDeclineText;
    static {
        CompletedDeclineText = "Sorry, your completion request has been declined. Try again, you have up to 3x! [kors.]";
    }

    public static String getCompletedDeclineText() {
        return CompletedDeclineText;
    }

    private static String NegotiatedAcceptText;
    static {
        NegotiatedAcceptText = "Your negotiation request has been accepted. The status has changed to Available Confirmed. [kors.]";
    }

    public static String getNegotiatedAcceptText() {
        return NegotiatedAcceptText;
    }

    private static String NegotiatedDeclineText;
    static {
        NegotiatedDeclineText = "Sorry, we have to decline your negotiation request. Your reasoning doesn't sound dramatic enough. [kors.]";
    }

    public static String getNegotiatedDeclineText() {
        return NegotiatedDeclineText;
    }

    private static String IncomingNegotiationText;
    static {
        IncomingNegotiationText = "You have a new negotiation request. Please check the List of Negotiation Request inside the Admin Room. [kors.]";
    }

    public static String getIncomingNegotiationText() {
        return IncomingNegotiationText;
    }
}
