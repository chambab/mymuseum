package mcloud.mm.identity.service;

import java.io.Closeable;
import java.io.IOException;
import java.util.Set;

import org.jclouds.ContextBuilder;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.jclouds.openstack.nova.v2_0.features.ServerApi;
import org.jclouds.rest.config.SetCaller.Module;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;






import java.io.IOException;
import java.util.Set;

import org.jclouds.openstack.nova.v2_0.NovaApi;

import com.google.common.collect.ImmutableSet;
import com.google.common.io.Closeables;

public class TestServer implements Closeable {

    private final NovaApi novaApi;
    private final Set<String> regions;

    public static void main(String[] args) throws IOException {
    	TestServer jcloudsNova = new TestServer();

        try {
            jcloudsNova.listServers();
            jcloudsNova.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jcloudsNova.close();
        }
    }

    public TestServer() {
    	
    	//Iterable<Module> modules = ImmutableSet.<Module>of(new SLF4JLoggingModule());
    	//Iterable<Module> modules = ImmutableSet.<Module> of(new Module());
        //Iterable<Module> modules = ImmutableSet.of

        String provider = "openstack-nova";
        String identity = "manager:manager"; // tenantName:userName
        String credential = "imsi00";

//        novaApi = ContextBuilder.newBuilder(provider)
//                .endpoint("http://192.168.145.130:5000/v2.0/")
//                .credentials(identity, credential)
//                .modules(modules)
//                .buildApi(NovaApi.class);
        novaApi = ContextBuilder.newBuilder(provider)
                .endpoint("http://192.168.145.130:5000/v2.0/")
                .credentials(identity, credential)
                //.modules(modules)
                .buildApi(NovaApi.class);
        
        regions = novaApi.getConfiguredRegions();
    }

    private void listServers() {
        for (String region : regions) {
            ServerApi serverApi = novaApi.getServerApi(region);
            

            System.out.println("Servers in " + region);

            for (Server server : serverApi.listInDetail().concat()) {
                System.out.println("  " + server);
            }
        }
    }

    public void close() throws IOException {
        Closeables.close(novaApi, true);
    }
}
