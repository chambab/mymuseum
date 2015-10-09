package mcloud.mm.core.weave.utils;

import org.jclouds.ContextBuilder;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.swift.v1.SwiftApi;


public class ConnectUtil {

	private NovaApi novaApi = null;
	private SwiftApi swiftApi = null;
	
	/**
	 * Return Openstack Nova API
	 * @return
	 * @throws Exception
	 */
    public NovaApi getNovaApi() throws Exception {
    	
        String provider = "openstack-nova";
        String identity = "manager:manager"; // tenantName:userName
        String credential = "imsi00";

        this.novaApi = ContextBuilder.newBuilder(provider)
                .endpoint("http://192.168.145.130:5000/v2.0/")
                .credentials(identity, credential)
                //.modules(modules)
                .buildApi(NovaApi.class);
        
        return this.novaApi;
    }	
}
