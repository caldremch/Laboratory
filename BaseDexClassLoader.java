
public class BaseDexClassLoader extends ClassLoader {


    @UnsupportedAppUsage
    private final DexPathList pathList;

    /**
     *
     * 用于一PathList 类和资源加载时, 所需要的依赖, 它总是会在加载 Class 或资源的时候, 进行检查
     */
    protected final ClassLoader[] sharedLibraryLoaders;

    /**
     * @param dexPath 包含额类资源的jar/apkjar/apk
     * @param optimizedDirectory 从 26 开始, 这个参数已经变为过期无效了
     * @param librarySearchPath so目录路径
     * {@code null}
     * @param parent 父类加载器
     * @param sharedLibraryLoaders 一般为 null
     */
    public BaseDexClassLoader(String dexPath,
                              String librarySearchPath, ClassLoader parent, ClassLoader[] sharedLibraryLoaders,
                              boolean isTrusted) {
        super(parent);
        //.....
        //创建DexPathList
        this.pathList = new DexPathList(this, dexPath, librarySearchPath, null, isTrusted);
    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        List<Throwable> suppressedExceptions = new ArrayList<Throwable>();
        Class c = pathList.findClass(name, suppressedExceptions);
        //....抛出在 pathList 找不到的类的异常
        return c;
    }


    @Override
    public String findLibrary(String name) {
        return pathList.findLibrary(name);
    }
}
