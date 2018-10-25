(ns ^:figwheel-hooks browser-headsup.ui
  (:require
   [reagent.core :as r]
   [browser-headsup.core :as headsup]))


(def col-2 "#F6F8F4")
(def col-3 "#9DA3A9")
(def col-4 "#ED9C19")
(def col-5 "#C74D3E")
(def col-bg "#1C222E")

(def spacing "0.5em")

(defn toggler
  [db]
  [:div
   {:style {:position :fixed
            :bottom 0
            :right "2em"
            :z-index 999

            :font-size "8pt"
            :line-height "80%"
            :padding spacing

            :background-color col-4
            :color col-bg
            :box-shadow "2px -1px 3px 0px rgba(0,0,0,0.3)"
            :cursor :pointer}
    :on-click (:on-toggle db)}
   (:title db)])


(defn tab
  [db tab]
  [:div
   {:style {:margin-right "1.5em"
            :font-weight (if (= (:id tab) (:selected-tab db)) :bold :normal)
            :cursor :pointer}
    :on-click #((:on-tab db) (:id tab))}
   (:title tab)])


(defn tab-content
  [db tab]
  [:div
   (:component tab)])


(defn header
  [db]
  [:div
   {:style {:display :flex
            :padding spacing
            :background-color col-4
            :color col-bg}}
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
            :height (if (:minimized? db) "0px" "50%")

            :font-size "10pt"
            :line-height "13pt"

            :background-color col-bg
            :color col-3
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
