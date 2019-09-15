package io.sumac.propertyinjector;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Properties;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import io.sumac.propertyinjector.sample.Model1;
import io.sumac.propertyinjector.sample.Model10;
import io.sumac.propertyinjector.sample.Model11;
import io.sumac.propertyinjector.sample.Model12;
import io.sumac.propertyinjector.sample.Model13;
import io.sumac.propertyinjector.sample.Model14;
import io.sumac.propertyinjector.sample.Model15;
import io.sumac.propertyinjector.sample.Model16;
import io.sumac.propertyinjector.sample.Model17;
import io.sumac.propertyinjector.sample.Model2;
import io.sumac.propertyinjector.sample.Model3;
import io.sumac.propertyinjector.sample.Model4;
import io.sumac.propertyinjector.sample.Model5;
import io.sumac.propertyinjector.sample.Model6;
import io.sumac.propertyinjector.sample.Model7;
import io.sumac.propertyinjector.sample.Model8;
import io.sumac.propertyinjector.sample.Model9;
import io.sumac.propertyresolver.PropertyResolver;

public class PropertyInjectorTest {

	private static final Logger LOGGER = LogManager.getLogger(PropertyInjectorTest.class);

	private BiConsumer<String, String> customLoggingInspector = (key, value) -> {
		LOGGER.debug("{}={}", key, value);
	};

	private Consumer<String> customLoggingPropertyNotFoundHandler = key -> {
		LOGGER.warn("{}=<NULL>", key);
	};

	private UnaryOperator<String> customToUpperCaseTransformer = s -> s.toUpperCase();

	@Test
	public void toTest_fields() {
		PropertyResolver resolver = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
		PropertyInjector systemUnderTest = PropertyInjector.fromPropertyResolver(resolver);
		Model1 output = systemUnderTest.to(Model1.class);
		validate(output);
	}

	@Test
	public void toTest_methods() {
		PropertyResolver resolver = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
		PropertyInjector systemUnderTest = PropertyInjector.fromPropertyResolver(resolver);
		Model2 output = systemUnderTest.to(Model2.class);
		validate(output);
	}

	@Test
	public void toTest_parameters() {
		PropertyResolver resolver = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
		PropertyInjector systemUnderTest = PropertyInjector.fromPropertyResolver(resolver);
		Model3 output = systemUnderTest.to(Model3.class);
		validate(output);
	}

	@Test
	public void fillInTest_fields() {
		PropertyResolver resolver = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
		PropertyInjector systemUnderTest = PropertyInjector.fromPropertyResolver(resolver);
		Model1 model = new Model1();
		systemUnderTest.fillIn(model);
		validate(model);
	}

	@Test
	public void fillInTest_methods() {
		PropertyResolver resolver = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
		PropertyInjector systemUnderTest = PropertyInjector.fromPropertyResolver(resolver);
		Model2 model = new Model2();
		systemUnderTest.fillIn(model);
		validate(model);
	}

	@Test
	public void toTest_missingFields() {
		PropertyResolver resolver = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
		PropertyInjector systemUnderTest = PropertyInjector.fromPropertyResolver(resolver);
		PropertyInjectorException output = assertThrows(PropertyInjectorException.class,
				() -> systemUnderTest.to(Model4.class));
		assertThat(output.getMessage(), is("Property not found: 'test.not_found.string'"));
	}

	@Test
	public void toTest_missingMethods() {
		PropertyResolver resolver = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
		PropertyInjector systemUnderTest = PropertyInjector.fromPropertyResolver(resolver);
		PropertyInjectorException output = assertThrows(PropertyInjectorException.class,
				() -> systemUnderTest.to(Model5.class));
		assertThat(output.getMessage(), is("Property not found: 'test.not_found.string'"));
	}

	@Test
	public void toTest_missingParameters() {
		PropertyResolver resolver = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
		PropertyInjector systemUnderTest = PropertyInjector.fromPropertyResolver(resolver);
		PropertyInjectorException output = assertThrows(PropertyInjectorException.class,
				() -> systemUnderTest.to(Model6.class));
		assertThat(output.getMessage(), is("Property not found: 'test.not_found.string'"));
	}

	@Test
	public void toTest_optionalFields() {
		PropertyResolver resolver = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
		PropertyInjector systemUnderTest = PropertyInjector.fromPropertyResolver(resolver);
		Model7 output = systemUnderTest.to(Model7.class);
		assertAll(() -> assertThat(output.getFoundString(), is("HELLO WORLD")),
				() -> assertThat(output.getNotFoundString(), nullValue()));
	}

	@Test
	public void toTest_optionalMethods() {
		PropertyResolver resolver = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
		PropertyInjector systemUnderTest = PropertyInjector.fromPropertyResolver(resolver);
		Model8 output = systemUnderTest.to(Model8.class);
		assertAll(() -> assertThat(output.getFoundString(), is("HELLO WORLD")),
				() -> assertThat(output.getNotFoundString(), nullValue()));
	}

	@Test
	public void toTest_optionalParameters() {
		PropertyResolver resolver = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
		PropertyInjector systemUnderTest = PropertyInjector.fromPropertyResolver(resolver);
		Model9 output = systemUnderTest.to(Model9.class);
		assertAll(() -> assertThat(output.getFoundString(), is("HELLO WORLD")),
				() -> assertThat(output.getNotFoundString(), nullValue()));
	}

	@Test
	public void toTestInvalidTypeField() {
		PropertyResolver resolver = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
		PropertyInjector systemUnderTest = PropertyInjector.fromPropertyResolver(resolver);
		PropertyInjectorException output = assertThrows(PropertyInjectorException.class,
				() -> systemUnderTest.to(Model10.class));
		assertThat(output.getMessage(), is("Field type not supported: class java.util.Date"));
	}

	@Test
	public void toTestInvalidTypeMethod() {
		PropertyResolver resolver = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
		PropertyInjector systemUnderTest = PropertyInjector.fromPropertyResolver(resolver);
		PropertyInjectorException output = assertThrows(PropertyInjectorException.class,
				() -> systemUnderTest.to(Model11.class));
		assertThat(output.getMessage(), is("Parameter type not supported: class java.util.Date"));
	}

	@Test
	public void toTestInvalidTypeParameters() {
		PropertyResolver resolver = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
		PropertyInjector systemUnderTest = PropertyInjector.fromPropertyResolver(resolver);
		PropertyInjectorException output = assertThrows(PropertyInjectorException.class,
				() -> systemUnderTest.to(Model12.class));
		assertThat(output.getMessage(), is("Parameter type not supported: class java.util.Date"));
	}

	@Test
	public void toTestTooManyConstructors() {
		PropertyResolver resolver = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
		PropertyInjector systemUnderTest = PropertyInjector.fromPropertyResolver(resolver);
		PropertyInjectorException output = assertThrows(PropertyInjectorException.class,
				() -> systemUnderTest.to(Model13.class));
		assertThat(output.getMessage(), is("Too many constructors: 2"));
	}

	@Test
	public void toTestParameterArgNotAnnotated() {
		PropertyResolver resolver = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
		PropertyInjector systemUnderTest = PropertyInjector.fromPropertyResolver(resolver);
		PropertyInjectorException output = assertThrows(PropertyInjectorException.class,
				() -> systemUnderTest.to(Model14.class));
		assertThat(output.getMessage(), is("Parameter not annotated: arg1"));
	}

	@Test
	public void toTestNoSetterArgs() {
		PropertyResolver resolver = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
		PropertyInjector systemUnderTest = PropertyInjector.fromPropertyResolver(resolver);
		PropertyInjectorException output = assertThrows(PropertyInjectorException.class,
				() -> systemUnderTest.to(Model15.class));
		assertThat(output.getMessage(), is("No arguments: setStr"));
	}

	@Test
	public void toTestTooManySetterArgs() {
		PropertyResolver resolver = PropertyResolver.registerProviders().addClasspathPropertiesFile("test.properties")
				.useCustomInspector(customLoggingInspector)
				.useCustomPropertyNotFoundHandler(customLoggingPropertyNotFoundHandler)
				.useCustomTransformer(customToUpperCaseTransformer).build();
		PropertyInjector systemUnderTest = PropertyInjector.fromPropertyResolver(resolver);
		PropertyInjectorException output = assertThrows(PropertyInjectorException.class,
				() -> systemUnderTest.to(Model16.class));
		assertThat(output.getMessage(), is("Too many arguments: setStr: 2"));
	}

	@Test
	public void testFromProperties() {
		Properties props = new Properties();
		props.put("test.found.string", "hello world");
		PropertyInjector systemUnderTest = PropertyInjector.fromProperties(props);
		Model17 output = systemUnderTest.to(Model17.class);
		assertThat(output.getStr(), is("hello world"));
	}

	private void validate(Model1 model) {
		assertAll(() -> assertThat(model.getFoundString(), is("HELLO WORLD")),
				() -> assertThat(model.getFoundInteger(), is(32)),
				() -> assertThat(model.getFoundIntegerPrimitive(), is(32)),
				() -> assertThat(model.getFoundLong(), is(64L)),
				() -> assertThat(model.getFoundLongPrimitive(), is(64L)),
				() -> assertThat(model.getFoundDouble(), is(2.2)),
				() -> assertThat(model.getFoundDoublePrimitive(), is(2.2)),
				() -> assertThat(model.getFoundFloat(), is(1.1F)),
				() -> assertThat(model.getFoundFloatPrimitive(), is(1.1F)),
				() -> assertThat(model.getFoundBoolean(), is(true)),
				() -> assertThat(model.getFoundBooleanPrimitive(), is(true)));
	}

	private void validate(Model2 model) {
		assertAll(() -> assertThat(model.getFoundString(), is("HELLO WORLD")),
				() -> assertThat(model.getFoundInteger(), is(32)),
				() -> assertThat(model.getFoundIntegerPrimitive(), is(32)),
				() -> assertThat(model.getFoundLong(), is(64L)),
				() -> assertThat(model.getFoundLongPrimitive(), is(64L)),
				() -> assertThat(model.getFoundDouble(), is(2.2)),
				() -> assertThat(model.getFoundDoublePrimitive(), is(2.2)),
				() -> assertThat(model.getFoundFloat(), is(1.1F)),
				() -> assertThat(model.getFoundFloatPrimitive(), is(1.1F)),
				() -> assertThat(model.getFoundBoolean(), is(true)),
				() -> assertThat(model.getFoundBooleanPrimitive(), is(true)));
	}

	private void validate(Model3 model) {
		assertAll(() -> assertThat(model.getFoundString(), is("HELLO WORLD")),
				() -> assertThat(model.getFoundInteger(), is(32)),
				() -> assertThat(model.getFoundIntegerPrimitive(), is(32)),
				() -> assertThat(model.getFoundLong(), is(64L)),
				() -> assertThat(model.getFoundLongPrimitive(), is(64L)),
				() -> assertThat(model.getFoundDouble(), is(2.2)),
				() -> assertThat(model.getFoundDoublePrimitive(), is(2.2)),
				() -> assertThat(model.getFoundFloat(), is(1.1F)),
				() -> assertThat(model.getFoundFloatPrimitive(), is(1.1F)),
				() -> assertThat(model.getFoundBoolean(), is(true)),
				() -> assertThat(model.getFoundBooleanPrimitive(), is(true)));
	}
}
