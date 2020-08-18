import com.android.build.gradle.AppExtension
import com.android.build.gradle.internal.dsl.BuildType
import com.android.build.gradle.internal.dsl.SigningConfigFactory
import com.android.builder.model.SigningConfig
import com.android.builder.signing.DefaultSigningConfig
import com.android.ide.common.signing.KeystoreHelper
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

/**
 *
 * @author Caldremch
 *
 * @date 2020-08-18
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class LaboratoryPlugin : BasePlugin<Project>() {

    override fun apply(project: Project) {
        var isApp = true
        val taskName = project.displayName
        if (!project.hasProperty("isApp")) {
            println("$taskName is running as Library")
        } else {
            println("$taskName is running as Application")
            isApp = getBoolean(project.properties["isApp"] as String)
        }
        if (isApp) {
            configSign(project)
        }
    }


    /**
     * @param isApp 组件以App运行时, 自动添加签名, 解除繁琐操作
     */
    private fun configSign(project: Project) {
        //获取 buildTypes 节点
        val android = project.extensions.getByType(AppExtension::class.java)
        val buildTypesClosure: NamedDomainObjectContainer<BuildType> = android.buildTypes
//        val debug = buildTypesClosure.maybeCreate("debug")
//        val release= buildTypesClosure.maybeCreate("release")
        val s = com.android.build.gradle.internal.dsl.SigningConfig("debug")
        println("default key folder: ${KeystoreHelper.defaultDebugKeystoreLocation()}")
        buildTypesClosure.register("release")
//        println("sign info: ${release.signingConfig.toString()}")
//        println("sign info: ${release.signingConfig.toString()}")
//        println(
//            "sign info: ${buildTypesClosure.}")
//        val config = DefaultSigningConfig()
//        release.signingConfig = config as SigningConfig
//        debug.buildConfigField("boolean", "minifyEnabled", "false")
            }
}