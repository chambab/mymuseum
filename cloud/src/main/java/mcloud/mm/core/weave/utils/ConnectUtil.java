package mcloud.mm.core.weave.utils;

import org.jclouds.ContextBuilder;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.openstack.keystone.v2_0.KeystoneApi;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.swift.v1.SwiftApi;
import org.jclouds.rest.config.SetCaller.Module;

import com.google.common.collect.ImmutableSet;


public class ConnectUtil {

	private NovaApi novaApi = null;
	private SwiftApi swiftApi = null;
	private KeystoneApi keystoneApi = null;
    
	private String identity = "manager:manager"; // tenantName:userName
	private String credential = "imsi00";
	private String endpoint = "http://192.168.145.130:5000/v2.0/";
	
	/**
	 * Return Openstack Nova API
	 * @return
	 * @throws Exception
	 */
    public NovaApi getNovaApi() throws Exception {
    	
        String provider = "openstack-nova";

        this.novaApi = ContextBuilder.newBuilder(provider)
                .endpoint(endpoint)
                .credentials(identity, credential)
                //.modules(modules)
                .buildApi(NovaApi.class);
        
        return this.novaApi;
    }
    /**
     * Get openstack keystone api
     * @return
     * @throws Exception
     */
    public KeystoneApi getKeystoneApi() throws Exception {
    	String provider = "openstack-keystone";
    	
 
    	keystoneApi = ContextBuilder.newBuilder(provider)
                .endpoint(endpoint)
                .credentials(identity, credential)
                //.modules(modules)
                .buildApi(KeystoneApi.class);
    	
    	return keystoneApi;
    }
}
