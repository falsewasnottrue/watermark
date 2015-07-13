import play.api.test.FakeApplication
import play.api.{DefaultGlobal, GlobalSettings}
import play.api.mvc.Handler

class EmptyFakeApplication(override val withRoutes: PartialFunction[(String, String), Handler] = PartialFunction.empty, override val withoutPlugins: Seq[String] = Seq("is24.modis.reporting.MetricsPlugin"), override val withGlobal: Option[GlobalSettings] = Some(DefaultGlobal), override val additionalConfiguration: Map[String, Any] = Map()) extends FakeApplication(withGlobal = withGlobal, additionalConfiguration = additionalConfiguration, withRoutes = withRoutes, withoutPlugins = withoutPlugins)