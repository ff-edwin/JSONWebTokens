package system;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;

import com.mendix.core.Core;
import com.mendix.core.component.LocalComponent;
import com.mendix.core.component.MxRuntime;
import com.mendix.integration.Integration;

@Component(immediate = true, properties = {"event.topics:String=com/mendix/events/model/loaded"})
public class UserActionsRegistrar implements EventHandler
{
	private MxRuntime mxRuntime;
	private LocalComponent component;
	private Integration integration;
	
	@Reference
	public void setMxRuntime(MxRuntime runtime)
	{
		mxRuntime = runtime;
		mxRuntime.bundleComponentLoaded();
	}
	
	@Reference
	public void setIntegration(Integration integration)
	{
		this.integration = integration;
	}
	
	@Override
	public void handleEvent(Event event)
	{
		if (event.getTopic().equals(com.mendix.core.event.EventConstants.ModelLoadedTopic()))        
		{
			component = mxRuntime.getMainComponent();
			Core.initialize(component, integration);   
			component.actionRegistry().registerUserAction(appcloudservices.actions.GenerateRandomPassword.class);
			component.actionRegistry().registerUserAction(appcloudservices.actions.LogOutUser.class);
			component.actionRegistry().registerUserAction(appcloudservices.actions.StartSignOnServlet.class);
			component.actionRegistry().registerUserAction(deeplink.actions.ExecuteDeeplink.class);
			component.actionRegistry().registerUserAction(deeplink.actions.GetApplicationURL.class);
			component.actionRegistry().registerUserAction(deeplink.actions.GetCurrentSessionID.class);
			component.actionRegistry().registerUserAction(deeplink.actions.ReadMicroflows.class);
			component.actionRegistry().registerUserAction(deeplink.actions.StartDeeplinkJava.class);
			component.actionRegistry().registerUserAction(jsonwebtoken.actions.JA_DecodeClaim.class);
			component.actionRegistry().registerUserAction(jsonwebtoken.actions.JA_DecodeHeaderClaim.class);
			component.actionRegistry().registerUserAction(jsonwebtoken.actions.JA_DecodeToken.class);
			component.actionRegistry().registerUserAction(jsonwebtoken.actions.JA_GenerateToken.class);
			component.actionRegistry().registerUserAction(jsonwebtoken.actions.JA_VerifyToken.class);
			component.actionRegistry().registerUserAction(system.actions.VerifyPassword.class);
		}
	}
}