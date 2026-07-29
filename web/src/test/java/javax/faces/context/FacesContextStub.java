package javax.faces.context;

/**
 * Test-only helper that exposes the protected
 * {@link FacesContext#setCurrentInstance(FacesContext)} static method to test
 * classes in other packages. This lets unit tests install a mock
 * {@code FacesContext} as the current instance without relying on the inline
 * mock maker (which is incompatible with {@code FacesContext} on Java 8).
 */
public final class FacesContextStub {

    private FacesContextStub() {
    }

    public static void setCurrent(FacesContext context) {
        FacesContext.setCurrentInstance(context);
    }

    public static void clear() {
        FacesContext.setCurrentInstance(null);
    }
}
