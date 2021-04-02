/**
 * Dex & Path  一对, 就是 dex 和 path 的这对 的列表结合
 * 这个类还包含了使用这些列表查找的方法
 * @hide
 */
public final class DexPathList {
    private static final String DEX_SUFFIX = ".dex";
    private static final String zipSeparator = "!/";

    /** class definition context */
    @UnsupportedAppUsage
    private final ClassLoader definingContext;

    /**
     * dex元素结合
     */
    @UnsupportedAppUsage
    private Element[] dexElements;

    /** so文件 */
    @UnsupportedAppUsage
    private final List<File> nativeLibraryDirectories;

    /** 系统so文件 */
    @UnsupportedAppUsage
    private final List<File> systemNativeLibraryDirectories;


    DexPathList(ClassLoader definingContext, String dexPath,
                String librarySearchPath, File optimizedDirectory, boolean isTrusted) {

        //...这里做了optimizedDirectory的存在,读写权限的校验问题, 因为上层传递的值都是 null(8.0 26 以后), 所以这里的判断基本上没有什么意义

        // save dexPath for BaseDexClassLoader
        //splitDexPath(dexPath) 手机 dexPath 目录下的所有文件(不包含目录)
        //获取 dex 的文件列表结合, 保存下来
        this.dexElements = makeDexElements(splitDexPath(dexPath), optimizedDirectory,
                suppressedExceptions, definingContext, isTrusted);
    }



    /**
     * 创建 Dex 元素集合
     */
    private static Element[] makeDexElements(List<File> files, File optimizedDirectory,
                                             List<IOException> suppressedExceptions, ClassLoader loader, boolean isTrusted) {
        Element[] elements = new Element[files.size()];
        for (File file : files) {
            //....
            dex = loadDexFile(file, optimizedDirectory, loader, elements);
            //....
            elements[elementsPos++] = new Element(dex, null);
            //....
        }
        return elements;
    }

    @UnsupportedAppUsage
    private static DexFile loadDexFile(File file, File optimizedDirectory, ClassLoader loader,
                                       Element[] elements)
            throws IOException {
        //最终会调用 DexFile 的 openDexFileNative来打开 Dex
    }


    /**
     * 寻找 Class
     */
    public Class<?> findClass(String name, List<Throwable> suppressed) {
        for (Element element : dexElements) {
            Class<?> clazz = element.findClass(name, definingContext, suppressed);
            if (clazz != null) {
                return clazz;
            }
        }
        return null;
    }


    /**
     * Element of the dex/resource path. Note: should be called DexElement, but apps reflect on
     * this.
     */
     static class Element {
        @UnsupportedAppUsage
        private final File path;
        /** Whether {@code path.isDirectory()}, or {@code null} if {@code path == null}. */
        private final Boolean pathIsDirectory;

        @UnsupportedAppUsage
        private final DexFile dexFile;

        @UnsupportedAppUsage
        public Element(DexFile dexFile, File dexZipPath) {
            this.dexFile = dexFile;
            this.path = dexZipPath;
            this.pathIsDirectory = (path == null) ? null : path.isDirectory();
        }

        public Class<?> findClass(String name, ClassLoader definingContext,
                                  List<Throwable> suppressed) {
            return dexFile != null ? dexFile.loadClassBinaryName(name, definingContext, suppressed)
                    : null;
        }
    }

}
