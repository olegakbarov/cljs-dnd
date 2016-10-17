
(ns cljs-dnd.backends.html5)
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require
    [cljs-dnd.state :refer [state]]
    [cljs.core.async :refer [<! put! chan timeout]]
    [goog.events :as events])
  (:import [goog.events EventType]))

(defn register-source [])

(defn register-target [])

(defn handle-drag-start [])

(defn handle-drag [])

;; is there a such thing??
(defn handle-drag-end [])

(defn handle-drop [])


;;----------------------------------------------------------------------
;;----------------------------------------------------------------------

(def dnd-chan (chan))

(defn handle-drag-start [e]
  (let [{:keys [drag-index item-type]} e
        updated {:drag-index drag-index
                 :item-type item-type}]
      (swap! state merge updated)))

(defn handle-drag-enter [e]
  (let [{:keys [dix hix]} @state]))
  ;; TODO update accrordin to event

(defn handle-drag-leave [e]
  (let [{:keys [dix hix]} @state]))
  ;; TODO update accrordin to event

(defn handle-drag-end [e]
  (let [{:keys [dix hix]} @state]))
  ;; TODO update accrordin to event

(defn handle-drag [e]
  (swap! state assoc :y (.-clientY e)))
  ;; TODO update accrordin to event

(defn handle-drag-over [e]
  (let [{:keys [dix hix]} @state]))
  ;; TODO update accrordin to event

(defn handle-drop [e])
  ;; TODO update accrordin to event


;; --------------------------
;; EVENTS:

(defn parse-event
  "Normalizes the event for dnd purposes"
  [e]
  (let [event-type (-> e .-type)
        item-index (-> e .-target .-dataset .-index int)
        bottom (-> e .-target .getBoundingClientRect .-bottom)
        top (-> e .-target .getBoundingClientRect .-top)]
    {:event-type event-type
     :item-index item-index
     :top top
     :bottom bottom}))

(defn on-event [e]
  (put! dnd-chan (parse-event e)))


;; --------------------------
;; LISTENER:

(defn dnd-start! [backend]
  (events/listen js/window EventType.DRAGSTART on-event)
  (events/listen js/window EventType.DRAGOVER  on-event)
  (events/listen js/window EventType.DRAGEND   on-event)
  (events/listen js/window EventType.DRAG      on-event)
  (events/listen js/window EventType.DRAGENTER on-event)
  (events/listen js/window EventType.DRAGLEAVE on-event)

  (go-loop []
     (let [e (<! dnd-chan)
           {:keys [event-type]} e]
        (condp = event-type
          "dragenter" (handle-drag-enter e)
          "dragleave" (handle-drag-leave e)
          "dragstart" (handle-drag-start e)
          "dragover"  (handle-drag-over e)
          "drag"      (handle-drag e)
          "drop"      (handle-drop e)
          :default    (prn event-type))
       (recur))))
