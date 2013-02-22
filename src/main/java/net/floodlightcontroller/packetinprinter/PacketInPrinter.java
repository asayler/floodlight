package net.floodlightcontroller.packetinprinter;

import java.util.Collection;
import java.util.Map;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.Set;

import org.openflow.protocol.OFMessage;
import org.openflow.protocol.OFType;
import org.openflow.util.HexString;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.floodlightcontroller.core.FloodlightContext;
import net.floodlightcontroller.core.IOFMessageListener;
import net.floodlightcontroller.core.IOFSwitch;
import net.floodlightcontroller.core.module.FloodlightModuleContext;
import net.floodlightcontroller.core.module.FloodlightModuleException;
import net.floodlightcontroller.core.module.IFloodlightModule;
import net.floodlightcontroller.core.module.IFloodlightService;
import net.floodlightcontroller.core.IFloodlightProviderService;
import net.floodlightcontroller.packet.Ethernet;

public class PacketInPrinter implements IOFMessageListener, IFloodlightModule {

    protected IFloodlightProviderService floodlightProvider;
    protected static Logger logger;

    @Override
	public String getName() {
	return PacketInPrinter.class.getSimpleName();
    }

    @Override
	public boolean isCallbackOrderingPrereq(OFType type, String name) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
	public boolean isCallbackOrderingPostreq(OFType type, String name) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
	public Collection<Class<? extends IFloodlightService>> getModuleServices() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
	public Map<Class<? extends IFloodlightService>, IFloodlightService> getServiceImpls() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
	public Collection<Class<? extends IFloodlightService>> getModuleDependencies() {
	Collection<Class<? extends IFloodlightService>> l =
	    new ArrayList<Class<? extends IFloodlightService>>();
	l.add(IFloodlightProviderService.class);
	return l;
    }

    @Override
	public void init(FloodlightModuleContext context) throws FloodlightModuleException {
	floodlightProvider = context.getServiceImpl(IFloodlightProviderService.class);
	logger = LoggerFactory.getLogger(PacketInPrinter.class);
    }

    @Override
	public void startUp(FloodlightModuleContext context) {
	floodlightProvider.addOFMessageListener(OFType.PACKET_IN, this);
    }

    @Override
	public Command receive(IOFSwitch sw, OFMessage msg, FloodlightContext cntx) {
	Ethernet eth =
	    IFloodlightProviderService.bcStore.get(cntx,
						   IFloodlightProviderService.CONTEXT_PI_PAYLOAD);
	logger.info("PacketIn Event on switch {}: \n{}\n",
		    sw.getId(), eth.toString());
	return Command.CONTINUE;
    }
    
}