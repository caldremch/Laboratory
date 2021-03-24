package com.tencent.matrix.trace;

import com.android.build.gradle.AppExtension;
import com.tencent.matrix.javalib.util.Log;
import com.tencent.matrix.trace.extension.MatrixExtension;
import com.tencent.matrix.trace.extension.MatrixTraceExtension;
import com.tencent.matrix.trace.transform.MatrixTraceTransformEx;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionAware;

/**
 * @auther Caldremch
 * @email finishmo@qq.com
 * @date 3/2/21 14:17
 * @description
 */
public class MatrixPluginEx implements Plugin<Project> {

    private final String TAG = "MatrixPluginEx";

    @Override
    public void apply(Project project) {

        project.getExtensions().create("matrix", MatrixExtension.class);
        ExtensionAware container = (ExtensionAware) project.getExtensions().findByName("matrix");
        container.getExtensions().create("trace", MatrixTraceExtension.class);
//        container.getExtensions().create("removeUnusedResources", MatrixDelUnusedResConfiguration.class);
        Configuration config = new Configuration();

        MatrixTraceTransformEx matrixTraceTransformEx = new MatrixTraceTransformEx();
        project.getExtensions().getByType(AppExtension.class).registerTransform(matrixTraceTransformEx);

        project.afterEvaluate(a -> {
            AppExtension android = (AppExtension) a.getExtensions().findByName("android");
            MatrixTraceExtension trace = (MatrixTraceExtension) container.getExtensions().getByName("trace");
            if (android == null) return;
            android.getApplicationVariants().all(applicationVariant -> {
                Log.i(TAG, "MatrixPluginEx:----->");
                MatrixTraceTransformEx.inject(project, trace, applicationVariant);
            });


////                if (configuration.removeUnusedResources.enable) {
////                    if (Util.isNullOrNil(configuration.removeUnusedResources.variant) || variant.name.equalsIgnoreCase(configuration.removeUnusedResources.variant)) {
////                        Log.i(TAG, "removeUnusedResources %s", configuration.removeUnusedResources)
////                        RemoveUnusedResourcesTask removeUnusedResourcesTask = project.tasks.create("remove" + variant.name.capitalize() + "UnusedResources", RemoveUnusedResourcesTask)
////                        removeUnusedResourcesTask.inputs.property(RemoveUnusedResourcesTask.BUILD_VARIANT, variant.name)
////                        project.tasks.add(removeUnusedResourcesTask)
////                        removeUnusedResourcesTask.dependsOn variant.packageApplication
////                        variant.assemble.dependsOn removeUnusedResourcesTask
////                    }
////                }

        });
    }
}
