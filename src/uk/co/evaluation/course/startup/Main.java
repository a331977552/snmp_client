package uk.co.evaluation.course.startup;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.List;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.TransportStateReference;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.transport.TransportListener;
import org.snmp4j.transport.UdpTransportMapping;

import uk.co.evaluation.course.client.Response;
import uk.co.evaluation.course.client.SnmpClient;

public class Main {

	
	public static void main(String[] args)throws IOException {
		//snmpwalk -v 2c -c coursework 52.25.129.28 memTotalReal.0
		
		SnmpClient client = SnmpClient.getClient();
		 Response response = client.sendSyn("1.3.6.1.2.1.1.1");
		 
		 VariableBinding variableBinding = response.getResult().get(0);
		 System.out.println( variableBinding.getVariable());
		 
		 
		
	
	
	}
}
