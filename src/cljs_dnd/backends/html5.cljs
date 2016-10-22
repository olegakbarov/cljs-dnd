
(ns cljs-dnd.backends.html5
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require
    [cljs-dnd.state :refer [state update-state!]]
    ; [cljs.core.async :refer [<! put! chan timeout]]
    [goog.events :as events])
  (:import [goog.events EventType]))


(defmulti handle-action
  (fn [type args]
    type))

(defmethod handle-action :default [type]
  (.log js/console "Unknown action: " type))

(defmethod handle-action :register-source [_ node]
  (do
    (aset node "draggable" true)
    (events/listen node EventType.DRAGSTART #(handle-action :drag-start %))))

(defmethod handle-action :register-target [])

(defmethod handle-action :drag-start [_ e]
  (update-state! :is-dragging true))

(defmethod handle-action :drop []
  (update-state! :is-dragging false))
