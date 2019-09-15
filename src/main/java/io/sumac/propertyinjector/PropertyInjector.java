package io.sumac.propertyinjector;

import static io.sumac.propertyinjector.TypeTransformer.isBoolean;
import static io.sumac.propertyinjector.TypeTransformer.isDouble;
import static io.sumac.propertyinjector.TypeTransformer.isFloat;
import static io.sumac.propertyinjector.TypeTransformer.isInt;
import static io.sumac.propertyinjector.TypeTransformer.isLong;
import static io.sumac.propertyinjector.TypeTransformer.isString;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import io.sumac.propertyinjector.annotations.Property;
import io.sumac.propertyresolver.PropertyResolver;

public class PropertyInjector {

	private final PropertyResolver resolver;

	private PropertyInjector(PropertyResolver resolver) {
		this.resolver = resolver;
	}

	public static PropertyInjector fromProperties(Properties properties) {
		return new PropertyInjector(PropertyResolver.registerProviders().useProperties(properties).build());
	}

	public static PropertyInjector fromPropertyResolver(PropertyResolver properties) {
		return new PropertyInjector(properties);
	}

	public final <T> T to(Class<T> type) {
		try {
			validateConstructor(type);
			T clazz = construct(type);
			fillIn(clazz);
			return clazz;
		} catch (PropertyInjectorException e) {
			throw e;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| SecurityException e) {
			throw PropertyInjectorException.wrapCheckedReflectionExceptions(e);
		}
	}

	public final void fillIn(Object obj) {
		try {
			populateFields(obj);
			populateBySetters(obj);
		} catch (PropertyInjectorException e) {
			throw e;
		} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
			throw PropertyInjectorException.wrapCheckedReflectionExceptions(e);
		}
	}

	private void validateConstructor(Class<?> type) {
		if (type.getDeclaredConstructors().length > 1) {
			throw PropertyInjectorException.tooManyConstructors(type.getDeclaredConstructors().length);
		}
		for (Constructor<?> constructor : type.getDeclaredConstructors()) {
			for (Parameter parameter : constructor.getParameters()) {
				if (!parameter.isAnnotationPresent(Property.class)) {
					throw PropertyInjectorException.constructorArgNotAnnotated(parameter);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private <T> T construct(Class<T> type)
			throws InstantiationException, IllegalAccessException, InvocationTargetException {
		List<Object> properties = new ArrayList<>();
		Constructor<?> constructor = type.getDeclaredConstructors()[0];
		constructor.setAccessible(true);
		Parameter[] parameters = constructor.getParameters();
		for (Parameter parameter : parameters) {
			Property property = parameter.getAnnotation(Property.class);
			if (isString(parameter.getType())) {
				properties.add(handleOptional(property, resolver.getString(property.name())));
			} else if (isLong(parameter.getType())) {
				properties.add(handleOptional(property, resolver.getLong(property.name())));
			} else if (isInt(parameter.getType())) {
				properties.add(handleOptional(property, resolver.getInt(property.name())));
			} else if (isDouble(parameter.getType())) {
				properties.add(handleOptional(property, resolver.getDouble(property.name())));
			} else if (isFloat(parameter.getType())) {
				properties.add(handleOptional(property, resolver.getFloat(property.name())));
			} else if (isBoolean(parameter.getType())) {
				properties.add(handleOptional(property, resolver.getBoolean(property.name())));
			} else {
				throw PropertyInjectorException.unsupportedType(parameter);
			}
		}
		return (T) constructor.newInstance(properties.toArray());
	}

	private <T> void populateFields(T obj) throws IllegalAccessException {
		for (Field field : obj.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(Property.class)) {
				setPrimitives(field, obj);
			}
		}
	}

	private void setPrimitives(Field field, Object obj) throws IllegalAccessException {
		boolean isAccessible = field.isAccessible();
		field.setAccessible(true);
		Property property = field.getAnnotation(Property.class);
		if (isString(field.getType())) {
			field.set(obj, handleOptional(property, resolver.getString(property.name())));
		} else if (isLong(field.getType())) {
			field.set(obj, handleOptional(property, resolver.getLong(property.name())));
		} else if (isInt(field.getType())) {
			field.set(obj, handleOptional(property, resolver.getInt(property.name())));
		} else if (isDouble(field.getType())) {
			field.set(obj, handleOptional(property, resolver.getDouble(property.name())));
		} else if (isFloat(field.getType())) {
			field.set(obj, handleOptional(property, resolver.getFloat(property.name())));
		} else if (isBoolean(field.getType())) {
			field.set(obj, handleOptional(property, resolver.getBoolean(property.name())));
		} else {
			throw PropertyInjectorException.unsupportedType(field);
		}
		field.setAccessible(isAccessible);
	}

	private <T> void populateBySetters(T obj) throws IllegalAccessException, InvocationTargetException {
		for (Method method : obj.getClass().getDeclaredMethods()) {
			if (method.isAnnotationPresent(Property.class)) {
				validateSetterMethod(method);
				setPrimitives(method, obj);
			}
		}
	}

	private void validateSetterMethod(Method method) {
		final int count = method.getParameterCount();
		if (count < 1) {
			throw PropertyInjectorException.noSetterArgs(method);
		} else if (count > 1) {
			throw PropertyInjectorException.tooManySetterArgs(method);
		}
	}

	private void setPrimitives(Method method, Object obj) throws IllegalAccessException, InvocationTargetException {
		boolean isAccessible = method.isAccessible();
		method.setAccessible(true);
		Property property = method.getAnnotation(Property.class);
		Parameter parameter = method.getParameters()[0];
		if (isString(parameter.getType())) {
			method.invoke(obj, handleOptional(property, resolver.getString(property.name())));
		} else if (isLong(parameter.getType())) {
			method.invoke(obj, handleOptional(property, resolver.getLong(property.name())));
		} else if (isInt(parameter.getType())) {
			method.invoke(obj, handleOptional(property, resolver.getInt(property.name())));
		} else if (isDouble(parameter.getType())) {
			method.invoke(obj, handleOptional(property, resolver.getDouble(property.name())));
		} else if (isFloat(parameter.getType())) {
			method.invoke(obj, handleOptional(property, resolver.getFloat(property.name())));
		} else if (isBoolean(parameter.getType())) {
			method.invoke(obj, handleOptional(property, resolver.getBoolean(property.name())));
		} else {
			throw PropertyInjectorException.unsupportedType(parameter);
		}
		method.setAccessible(isAccessible);
	}

	private Object handleOptional(Property property, Optional<?> value) {
		if (property.optional()) {
			if (value.isPresent()) {
				return value.get();
			} else {
				return null;
			}
		} else {
			if (value.isPresent()) {
				return value.get();
			} else {
				throw PropertyInjectorException.propertyNotFound(property.name());
			}
		}
	}

}
