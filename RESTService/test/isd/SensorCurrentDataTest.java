package isd;

import org.junit.Rule;
import org.junit.runner.RunWith;

import com.eclipsesource.restfuse.Destination;
import com.eclipsesource.restfuse.HttpJUnitRunner;
import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.PollState;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.annotation.Context;
import com.eclipsesource.restfuse.annotation.HttpTest;
import com.eclipsesource.restfuse.annotation.Poll;

import static com.eclipsesource.restfuse.Assert.assertOk;

/**
 * Test case for REST API.
 * @author Nicolae
 *
 */
@RunWith( HttpJUnitRunner.class )
public class SensorCurrentDataTest {
	
	@Rule
	public Destination restfuse = new Destination(this, "http://localhost:8080/RESTService/sensor");

	@Context
	private Response response;
	
	@Context
	private PollState pollState;
	
	@HttpTest(method = Method.GET, path = "/current")
	@Poll(times = 3, interval = 2000)
	public void checkRestfuseOnlineStatus() {
		System.out.println("Attemt " + pollState.getTimes());
		System.out.println(
				pollState.getTimes() + ". Responsecode = " + pollState.getResponse(pollState.getTimes()).getStatus());
	}

	@HttpTest(method = Method.GET, path = "/current")
	public void checkRestfuseOnlineStatusWithHeader() {
		assertOk(response);
	}
}
