package ink.anh.api.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides a caching mechanism for method reflection to improve performance
 * by avoiding repeated lookup of methods on classes.
 */
public class ReflectionCache {
    /**
     * Cache storing method lookups. The cache keys are generated based on the class, method name,
     * and parameter types to ensure uniqueness.
     */
    private static final Map<String, Method> methodCache = new HashMap<>();

    /**
     * Retrieves a {@link Method} instance from the cache if present; otherwise, performs reflection
     * to find the method on the class, caches it, and returns it. This method also makes
     * the reflected method accessible, even if it is private.
     *
     * @param clazz The {@link Class} on which the method is declared.
     * @param methodName The name of the method to find.
     * @param parameterTypes The parameter types of the method, used to accurately identify methods
     *                       with the same name (method overloading).
     * @return The {@link Method} instance for the specified method name and parameters on the class.
     * @throws RuntimeException if the method cannot be found on the class.
     */
    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        String key = generateKey(clazz, methodName, parameterTypes);
        return methodCache.computeIfAbsent(key, k -> {
            try {
                Method method = clazz.getMethod(methodName, parameterTypes);
                method.setAccessible(true); // Ensures access to private methods.
                return method;
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Method not found", e);
            }
        });
    }

    /**
     * Generates a unique cache key for a method based on the class name, method name, and parameter types.
     * This ensures that different methods (including overloaded methods) have unique keys.
     *
     * @param clazz The class to which the method belongs.
     * @param methodName The name of the method.
     * @param parameterTypes The parameter types of the method.
     * @return A unique string key representing the method.
     */
    private static String generateKey(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        StringBuilder keyBuilder = new StringBuilder(clazz.getName()).append(".").append(methodName);
        for (Class<?> paramType : parameterTypes) {
            keyBuilder.append(":").append(paramType.getName());
        }
        return keyBuilder.toString();
    }
}
