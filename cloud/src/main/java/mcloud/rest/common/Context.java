package mcloud.rest.common;

import java.util.Date;

import org.jclouds.ContextBuilder;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.domain.Credentials;
import org.jclouds.openstack.keystone.v2_0.domain.Access;

import com.google.common.base.Function;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;

/**
 * 
 * @author hjaemoon
 *
 */
public class Context {
	private ContextBuilder contextBuilder = null;
	private Access access = null;
	private String provider = null;
	private String username = null;
	private String credential = null;
	private String endpoint = null;
	/**
	 * 
	 * @param provider openstack-nova
	 * @param username tenantName:userName
	 * @param credential password
	 * @throws Exception
	 */
	public Context(String provider, String username, 
			       String credential, String endpoint) throws Exception {
		this.provider = provider;
		this.username = username;
		this.credential = credential;
		this.endpoint = endpoint;
		
		contextBuilder = ContextBuilder.newBuilder(provider).credentials(username, credential);
	}
	
	public Access getUserContext() throws Exception {

		if(this.access != null) return this.access;
		
		ComputeServiceContext context = contextBuilder.buildView(ComputeServiceContext.class);
		Function<Credentials, Access> auth = context.utils().injector().getInstance(Key.get(new TypeLiteral<Function<Credentials, Access>>(){}));
		this.access = auth.apply(new Credentials.Builder<Credentials>().identity(username).credential(credential).build());
		return this.access;
	}
	/**
	 * Get user information
	 * @return
	 * @throws Exception
	 */
	public String getUserName() throws Exception {
		return this.access.getUser().getName();
	}
	public String getUserId() throws Exception {
		return this.access.getUser().getId();
	}
	public String getTenantName() throws Exception {
		return this.access.getToken().getTenant().get().getName();
	}
	public String getTenantId() throws Exception {
		return this.access.getToken().getTenant().get().getId();
	}
	public String getTokenId() throws Exception {
		return this.access.getToken().getId();
	}
	public Date getTokenExpires() throws Exception {
		return this.access.getToken().getExpires();
	}
}
