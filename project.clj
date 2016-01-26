(defproject minimax-server "0.1.0-SNAPSHOT"
  :description "A Tic Tac Toe minimax web api built on scarvill91/java-httpserver project"
  :url "http://107.170.25.194:5000/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :resource-paths ["lib/httpserver-1.0.jar"]
  :dependencies [[org.clojure/clojure "1.7.0-RC2"] [org.clojure/data.json "0.2.6"]]
  :profiles {:dev {:dependencies [[speclj "3.3.1"]]}}
  :plugins [[speclj "3.3.1"]]
  :test-paths ["spec"]
  :main minimax-server.core/main)
