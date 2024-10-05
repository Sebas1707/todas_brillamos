/*
* Backend-models: Código que contiene el modelo de Orders y sus atributos
* @author: Jennyfer Jasso
 */
package models

/*
 * Estructura de la tabla Orders
 */
type Orders struct {
	ID                 uint `json:"-" gorm:"primarykey"`
	Quantity           uint
	DeliveryDate       string  `gorm:"type:date"`
	Status             string  `gorm:"type:varchar(20)"`
	OrderReceivedDate  string  `gorm:"type:date"`
	PreparingOrderDate *string `json:"-" gorm:"type:date"`
	ShippedDate        *string `json:"-" gorm:"type:date"`
	ProductID          uint
	ClientID           uint
}
