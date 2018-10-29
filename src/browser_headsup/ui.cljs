(ns ^:figwheel-hooks browser-headsup.ui
  (:require
   [reagent.core :as r]
   [browser-headsup.core :as headsup]))


(def col-bg         "#292B2E")
(def col-bg2        "#212026")
(def col-error      "#ff5555")
(def col-default    "#B0ADAB")
(def col-default2   "#2C8554")
(def col-highlight  "#4494D7")
(def col-highlight2 "#BB6DBC")
(def col-dimmed     "#93643E")
(def spacing        "0.5em")
(def font-size      "9pt")
(def line-height    "10pt")


(defn toggler
  [db]
  [:div
   {:style {:position :fixed
            :bottom 0
            :right "2em"
            :z-index 999

            :font-size font-size
            :line-height line-height
            :padding spacing

            :background-color col-bg2
            :color col-highlight
            :font-family :monospace
            :font-weight :bold
            :box-shadow "2px -1px 3px 0px rgba(0,0,0,0.3)"
            :cursor :pointer}
    :on-click (:on-toggle db)}
   (:title db)])


(defn tab
  [db tab]
  [:div
   {:key (:id tab)
    :style {:margin-right "1.5em"
            :font-weight (if (= (:id tab) (:selected-tab db)) :bold :normal)
            :cursor :pointer}
    :on-click #((:on-tab db) (:id tab))}
   (or (:title tab) "???")])


(defn tab-content
  [db tab]
  [:div
   {:style {:font-size font-size}}
   (:component tab)])


(defn header
  [db]
  [:div
   {:style {:display :flex
            :padding spacing
            :background-color col-bg2
            :color col-dimmed}}
   (into [:div
          {:style {:display :flex}}
          (map #(tab db (get-in db [:tabs %])) (:tabs-order db))])])


(defn content
  [db]
  [:div
   {:style {:position :fixed
            :bottom 0
            :z-index 998
            :width "100%"
            :height (if (:minimized? db) "0px" (:size db))

            :font-size font-size
            :line-height line-height

            :background-color col-bg
            :color col-default
            :box-shadow "2px -1px 3px 0px rgba(0,0,0,0.3)"
            :transition "all 0.2s ease-in-out 0s"}}
   [:div
    {:style {:display (if (:minimized? db) :none :block)
             :height "100%"}}
    [header db]
    [:div
     {:style {:padding spacing
              :height "100%"
              :overflow :auto}}
     [tab-content db (get-in db [:tabs (:selected-tab db)])]]]])


(defn container
  [db]
  (let [db @headsup/!db]
    [:div
     [toggler db]
     [content db]]))


(def container-id "browser-headsup-container")


(defn ^:after-load update!
  []
  (r/render [container]
            (js/document.getElementById container-id)))


(defn install!
  []
  (.log js/console "install!")
  (if-not (.querySelector js/document (str "#" container-id))
    (let [el (.createElement js/document "div")]
      (.setAttribute el "id" container-id)
      (-> (.-body js/document)
          (.appendChild el))))
  (update!))
