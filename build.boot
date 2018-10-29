(def +version+ "1.0.2")

(set-env!
 :resource-paths #{"src"}
 :dependencies '[[witek/toolbox "1.0.1" :scope "test"]
                 [adzerk/bootlaces "0.1.13" :scope "test"]])

(require '[toolbox.boot.util])
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


