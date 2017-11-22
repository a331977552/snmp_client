package uk.co.evaluation.course.client;

import java.io.IOException;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.transport.UdpTransportMapping;
/**
 * a default sington snmp client.
 * 
 * @author cody
 *
 */
public class SnmpClient {

	private static final String SERVER_URL = "udp:52.25.129.28/161";
	//the community ,in my case, it is coursework
	private static final String COMMUNITY_ADDRESS = "coursework";
	//the number of retries
	private static final int TIMES_RETRY = 3;
	//time out 10 secs
	private static final int TIME_OUT = 10000;
	private static SnmpClient client=new SnmpClient();
	
	private Snmp defaultSnmp;
	private CommunityTarget defaultCommunityTarget;
	private  SnmpClient() {
			//construct an local address mapping		
				defaultSnmp = getDefaultSnmp();
				defaultCommunityTarget = createCommunityTarget();
				// using v2c version , so using PDU, if v3 version,should using ScopedPDU

	
	}
	
	public void sendAsyn(String oidValue,Object userObj,ResponseListener listener) {
		PDU pdu = generatePDU(oidValue);
		

		try {
			defaultSnmp.send(pdu,defaultCommunityTarget,userObj,listener);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	public void sendAsyn(String oidValue,ResponseListener listener) {
		PDU pdu = generatePDU(oidValue);
		
		
		try {
			defaultSnmp.send(pdu,defaultCommunityTarget,null,listener);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	public void sendAsyn(String oidValue,int type,Object userObj,ResponseListener listener) {
		PDU pdu = generatePDU(type,oidValue);
		
		
		try {
			defaultSnmp.send(pdu,defaultCommunityTarget,userObj,listener);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	public void sendAsyn(String oidValue,int type,ResponseListener listener) {
		PDU pdu = generatePDU(type,oidValue);
		
		
		try {
			defaultSnmp.send(pdu,defaultCommunityTarget,null,listener);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	public Response sendSyn(String oidValue,int type) {
		PDU pdu = generatePDU(type,oidValue);
		
		try {
			ResponseEvent send = defaultSnmp.send(pdu,defaultCommunityTarget);
			Response response = formatResult(send);
			return   response;
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return null;
	}

	private Response formatResult(ResponseEvent send) {
		Response response=new Response();
		int errorStatus = send.getResponse().getErrorStatus();
		if(errorStatus==SnmpConstants.SNMP_ERROR_SUCCESS) {
			VariableBinding variableBinding = send.getResponse().get(0);
			response.addResult(variableBinding);
			response.setSuccessful(true);
			response.setStatus(SnmpConstants.SNMP_ERROR_SUCCESS);
		}else {
			response.setStatus(errorStatus);
			String errorStatusText = send.getResponse().getErrorStatusText();
			response.setErrorStatusText(errorStatusText);
			response.setSuccessful(false);
		}
		return response;
	}
	
	/**
	 * 
	 * @param oidValue
	 * @return response
	 */
	public Response sendSyn(String oidValue) {
		PDU pdu = generatePDU(oidValue);
		
		try {
			ResponseEvent send = defaultSnmp.send(pdu,defaultCommunityTarget);
			
			Response response = formatResult(send);
			
			return   response;
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return null;
	}
	


	private PDU generatePDU(String oidValue) {
		PDU pdu=new PDU();
		//TODO set a type of report
		pdu.setType(PDU.GETNEXT);
		pdu.addOID(new VariableBinding(new OID(oidValue)));
		return pdu;
	}
	
	private PDU generatePDU(int type,String oidValue) {
		PDU pdu=new PDU();
		//TODO set a type of report
		pdu.setType(type);
		pdu.addOID(new VariableBinding(new OID(oidValue)));
		return pdu;
	}

	private CommunityTarget createCommunityTarget() {
         	
		
		
		CommunityTarget communityTarget=new CommunityTarget();
		Address address = GenericAddress.parse(SERVER_URL);
		communityTarget.setAddress(address);
		communityTarget.setCommunity(new OctetString(COMMUNITY_ADDRESS));
		//version
		communityTarget.setRetries(TIMES_RETRY);
		communityTarget.setTimeout(TIME_OUT);
		communityTarget.setVersion(SnmpConstants.version2c);
		return communityTarget;
	}
	
	public static SnmpClient getClient() {
		
		return client;
	}
	
	private Snmp getDefaultSnmp() {
		UdpTransportMapping target=null;
		try {
			target = new DefaultUdpTransportMapping();
			target.listen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Snmp snmp=new Snmp(target);

		return snmp;
	}
}
