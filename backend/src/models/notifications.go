package models

import (
	"time"

	"gorm.io/gorm"
)

type Notifications struct {
	gorm.Model
	Title    string
	Message  string
	ClientID uint
	IsRead   bool
	Type     string
	Priority string
	ReadAt   *time.Time
}

// Pendiente de conectar con la base de datos
func createNotification(clientID uint, title, message, notifyType, priority string) error {
	notifications := Notifications{
		Title:    title,
		Message:  message,
		ClientID: clientID,
		Type:     notifyType,
		Priority: priority,
		IsRead:   false,
	}

	// Pendiente añadirlo a la base de datos

	return nil
}

func MarkNotificationAsRead(notificationID uint) error {
	var notification Notifications
	//Queda pendiente implementación con la base de datos
}
