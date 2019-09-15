package io.sumac.propertyinjector;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class PropertyInjectorException extends RuntimeException {

	private static final long serialVersionUID = -1900333701211170974L;

	public PropertyInjectorException(String msg) {
		super(msg);
	}

	public PropertyInjectorException(String msg, Throwable t) {
		super(msg, t);
	}

	static PropertyInjectorException unsupportedType(Parameter parameter) {
		return new UnsupportedTypeException("Parameter type not supported: " + parameter.getType());
	}

	public static PropertyInjectorException propertyNotFound(String name) {
		return new PropertyNotFoundException("Property not found: '" + name + "'");
	}

	static PropertyInjectorException unsupportedType(Field field) {
		return new UnsupportedTypeException("Field type not supported: " + field.getType());
	}

	static PropertyInjectorException tooManyConstructors(int count) {
		return new BadConstructorException("Too many constructors: " + count);
	}

	static PropertyInjectorException constructorArgNotAnnotated(Parameter parameter) {
		return new BadConstructorException("Parameter not annotated: " + parameter.getName());
	}

	static PropertyInjectorException wrapCheckedReflectionExceptions(Exception e) {
		return new ReflectionErrorException("Reflection error", e);
	}

	static PropertyInjectorException tooManySetterArgs(Method method) {
		return new BadSetterMethodException(
				"Too many arguments: " + method.getName() + ": " + method.getParameterCount());
	}

	static PropertyInjectorException noSetterArgs(Method method) {
		return new BadSetterMethodException("No arguments: " + method.getName());
	}

	static class UnsupportedTypeException extends PropertyInjectorException {

		private static final long serialVersionUID = -8714851471408394841L;

		private UnsupportedTypeException(String msg) {
			super(msg);
		}
	}

	static class PropertyNotFoundException extends PropertyInjectorException {

		private static final long serialVersionUID = 6009742871391532466L;

		private PropertyNotFoundException(String msg) {
			super(msg);
		}
	}

	static class BadConstructorException extends PropertyInjectorException {

		private static final long serialVersionUID = -7701670656858139665L;

		private BadConstructorException(String msg) {
			super(msg);
		}
	}

	static class BadSetterMethodException extends PropertyInjectorException {

		private static final long serialVersionUID = 4765051551889948301L;

		private BadSetterMethodException(String msg) {
			super(msg);
		}
	}

	static class ReflectionErrorException extends PropertyInjectorException {

		/**
		 * 
		 */
		private static final long serialVersionUID = -3204896446146415988L;

		private ReflectionErrorException(String msg, Throwable t) {
			super(msg, t);
		}
	}

}
