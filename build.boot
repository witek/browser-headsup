(def +version+ "1.0.2")

(set-env!
 :resource-paths #{"src"}
 :dependencies '[[reagent "0.8.1"]
                 [adzerk/bootlaces "0.1.13" :scope "test"]])

(require '[adzerk.bootlaces :refer :all])

(bootlaces! +version+)

(task-options!

 pom {:project     'witek/browser-headsup
      :version     +version+
      :description "Heads-up display in the browser to display developer information."
      :developers  {"Witoslaw Koczewski" "wi@koczewski.de"}
      :url         "https://github.com/witek/browser-headsup"
      :scm         {:url "https://github.com/witek/browser-headsup.git"}
      :license     {"Eclipse Public License - v 2.0" "https://raw.githubusercontent.com/witek/bindscript/master/LICENSE"}})

