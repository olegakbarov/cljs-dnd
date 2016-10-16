(def project 'cljs-dnd)
(def version "0.1.0-SNAPSHOT")

(set-env! :resource-paths #{"resources" "src" "examples"}
          :source-paths   #{"test"}
          :dependencies '[[adzerk/boot-cljs           "1.7.228-1"  :scope "test"]
                          [adzerk/boot-cljs-repl      "0.3.0"      :scope "test"]
                          [adzerk/boot-reload         "0.4.8"      :scope "test"]
                          [pandeiro/boot-http         "0.7.2"      :scope "test"]
                          [com.cemerick/piggieback    "0.2.1"      :scope "test"]
                          [org.clojure/tools.nrepl    "0.2.12"     :scope "test"]
                          [weasel                     "0.7.0"      :scope "test"]
                          [org.clojure/clojurescript "1.7.228"]
                          [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                          [binaryage/devtools "0.6.1"]
                          [reagent "0.6.0-rc"]])

(task-options!
 pom {:project     project
      :version     version
      :description "Idiomatic drag-and-drop for ClojureScript applications with React rendering."
      ; :url         "http://example/FIXME"
      :scm         {:url "https://github.com/olegakbarov/cljs-dnd"}
      :license     {"Eclipse Public License"
                    "http://www.eclipse.org/legal/epl-v10.html"}})

(require
 '[adzerk.boot-cljs      :refer [cljs]]
 '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
 '[adzerk.boot-reload    :refer [reload]]
 '[pandeiro.boot-http    :refer [serve]])


(deftask build
  "Build and install the project locally."
  []
  (comp (pom)
        (jar)
        (install)))

(deftask run []
  (comp (serve)
        (watch)
        (cljs-repl)
        (reload)
        (cljs)))

(deftask development []
  ;; should run examples
  (task-options! cljs   {:optimizations :none :source-map true}
                 reload {:on-jsload 'cljs-dnd/examples.sort/mount-root})
  identity)

(deftask dev
  "Simple alias to run application in development mode"
  []
  (comp (development)
        (run)))
