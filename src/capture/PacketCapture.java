package capture;

import gui.HomeFrame;
import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;

/**
 * Created by User on 7/9/2016.
 */
public class PacketCapture {

    JpcapCaptor captor;

    public PacketCapture(){

        setupConnection();
    }

    private void setupConnection() {

        NetworkInterface[] devices = JpcapCaptor.getDeviceList();

        try {
            captor =JpcapCaptor.openDevice(devices[1], 65535, false, 20);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startCapture(DefaultTableModel tableModel) {


        while(HomeFrame.isCapturing()) {

            Packet packet = captor.getPacket();

            if (packet != null && packet instanceof TCPPacket) {

                TCPPacket tcpPacket = (TCPPacket) packet;
                tableModel.addRow(extractData(tcpPacket));
            }
        }

    }

    //extract info from tcpPacket obbject
    private String[] extractData(TCPPacket tcpPacket) {

        String src_ip = String.valueOf(tcpPacket.src_ip);
        String dst_ip = String.valueOf(tcpPacket.dst_ip);
        String src_port = String.valueOf(tcpPacket.src_port);
        String dst_port = String.valueOf(tcpPacket.dst_port);

        return new String[]{src_ip,dst_ip,src_port,dst_port};
    }
}
