package mcloud.mm.identity.web;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



import mcloud.mm.core.weave.controller.BaseController;
import mcloud.mm.identity.model.User;
import mcloud.mm.identity.service.IdentityService;

@Controller
@RequestMapping("/identity")
public class IdentityController extends BaseController {
	@Inject
	private IdentityService identityService;
	
	
	@RequestMapping(value="/1/msg/{userId}/MyMuseumList.json", method=RequestMethod.GET)	
	public @ResponseBody List selectMyMuseumList(@PathVariable String userId) {
		
		try {
			return identityService.selectMyMuseumList(userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/1/user/loginuser.json", method=RequestMethod.POST)
	public @ResponseBody User loginUser(@RequestBody User user) throws IOException {
		User rtnUser = null;
		try {
	
		} catch(Exception e) {
			this.getLogger().debug(e.toString());
		}
		
		if(rtnUser == null) {
			rtnUser = new User();
			rtnUser.setUserId("FAIL");			
		} else {

		}
		return rtnUser;
	}		
}
