package Agents.Receivers;
/**
 This example shows a minimal agent that will answer to one simple request
 The Agent also returns its name
 and wait for next message
 */
import Agents.MIB.Helpers;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class SimpleReceiverAgent extends Agent {

    protected void setup() {
        /** Registration with the DF */

        Helpers.registerDF(this, getName(), Helpers.RECEIVER_TYPE);

//        DFAgentDescription dfd = new DFAgentDescription();
//        ServiceDescription sd = new ServiceDescription();
//        sd.setType("ReceiverAgent");
//        sd.setName(getName());
//        sd.setOwnership("ExampleReceiversOfJADE");
//        sd.addOntologies("ReceiverAgent");
//        dfd.setName(getAID());
//        dfd.addServices(sd);
//        try {
//            DFService.register(this,dfd);
//        } catch (FIPAException e) {
//            System.err.println(getLocalName()+" registration with DF unsucceeded. Reason: "+e.getMessage());
//            doDelete();
//        }

        System.out.println("Hello World! My name is " + getAID().getLocalName());
        ReceiveMessage rm = new ReceiveMessage();
        addBehaviour(rm);
    }

    public class ReceiveMessage extends CyclicBehaviour {

        // Variable to Hold the content of the received Message
        private String MessagePerformative;
        private String MessageContent;
        private String SenderName;

        public void action() {
            //Receive a Message
            ACLMessage msg = receive();
            if(msg != null) {

                MessagePerformative = msg.getPerformative(msg.getPerformative());
                MessageContent = msg.getContent();
                SenderName = msg.getSender().getName();

                System.out.println("***I Received a Message***" +"\n"+
                        "The Sender Name is:"+ SenderName+"\n"+
                        "The Content of the Message is::> " + MessageContent+"\n"+
                        "::: And Performative is:: " + MessagePerformative);

                //Reply to the Message
                if (MessagePerformative.equals("REQUEST") && MessageContent.equals("Hello How Are You?")) {
                    ACLMessage out_msg = new ACLMessage(ACLMessage.INFORM);
                    out_msg.addReceiver(msg.getSender());
                    out_msg.setLanguage(msg.getLanguage());
                    out_msg.setContent("I am Fine Thank You");
                    send(out_msg);
                    System.out.println("****I Replied to::> " + SenderName + "***");
                    System.out.println("The Content of My Reply is:" + out_msg.getContent());
                    System.out.println("ooooooooooooooooooooooooooooooooooooooo");
                }
            }
        }
    }
}
