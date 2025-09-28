(defproject rest-api-sample "0.1.0-SNAPSHOT"
  :description "A sample REST API"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [ring/ring-core "1.9.4"]
                 [ring/ring-jetty-adapter "1.9.4"]
                 [ring/ring-defaults "0.3.4"]
                 [compojure "1.6.2"]
                 [http-kit "2.5.3"]
                 [clj-http "3.12.3"]]
  :main rest-api-sample.core
  :profiles {:uberjar {:aot :all}})
