package tests.br.ufsc.leb.adangomes.us.run;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import net.douglashiura.us.Fixture;
import net.douglashiura.us.run.Annotations;

@Fixture("AnnotationsTestFixture")
public class AnnotationsTest {

	@Test
	public void selfAnnotation() throws Exception {
		assertNotNull(Annotations.getFixture("AnnotationsTestFixture"));
	}

}
