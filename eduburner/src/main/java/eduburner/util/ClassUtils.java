package eduburner.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ClassUtils {

	/**
	 * 
	 * @param type
	 *            the (usable) super type if passed a CGLIB enhanced class
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Class checkForCGLIB(Class type) {
		if (type.getName().contains("CGLIB")) {
			return type.getSuperclass();
		} else {
			return type;
		}
	}

	// taken from springside
	/**
	 * Locates the first generic declaration on a class.
	 * 
	 * @param clazz
	 *            The class to introspect
	 * @return the first generic declaration, or <code>null</code> if cannot
	 *         be determined
	 */
	@SuppressWarnings("unchecked")
	public static Class getGenericClass(Class clazz) {
		return getGenericClass(clazz, 0);
	}

	/**
	 * Locates generic declaration by index on a class.
	 * 
	 * @param clazz
	 *            clazz The class to introspect
	 * @param index
	 *            the Index of the generic ddeclaration,start from 0.
	 */
	@SuppressWarnings("unchecked")
	public static Class getGenericClass(Class clazz, int index) {
		Type genType = clazz.getGenericSuperclass();

		if (genType instanceof ParameterizedType) {
			Type[] params = ((ParameterizedType) genType)
					.getActualTypeArguments();

			if ((params != null) && (params.length >= (index - 1))) {
				return (Class) params[index];
			}
		}
		return null;
	}
}
