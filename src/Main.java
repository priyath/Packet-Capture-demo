import capture.PacketCapture;
import gui.HomeFrame;
import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by User on 7/9/2016.
 */
public class Main {

    public static void main(String args[]) {

        //set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        PacketCapture packetCapture = new PacketCapture();
        HomeFrame homeFrame = new HomeFrame(packetCapture);

    }
}
