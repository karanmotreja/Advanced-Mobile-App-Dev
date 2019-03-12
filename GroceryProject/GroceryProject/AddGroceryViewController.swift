//
//  AddGroceryViewController.swift
//  GroceryProject
//
//  Created by Karan Motreja on 3/10/19.
//  Copyright © 2019 Karan Motreja. All rights reserved.
//

import UIKit

class AddGroceryViewController: UIViewController, UITextFieldDelegate {
    
    var grocery : Grocery?

    @IBOutlet weak var timePicker: UIDatePicker!
    
    @IBOutlet weak var groceryTextField: UITextField!
    
    @IBOutlet weak var saveButton: UIBarButtonItem!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.groceryTextField.delegate = self
        timePicker.minimumDate = NSDate() as Date
        timePicker.locale = NSLocale.current
    }
    
    func checkName()
    {
        let text = groceryTextField.text ?? ""
        saveButton.isEnabled = !text.isEmpty
    }

    func checkDate()
    {
        if NSDate().earlierDate(timePicker.date) == timePicker.date
        {
            saveButton.isEnabled = false
        }
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return true
    }
    
    func textFieldDidEndEditing(_ textField: UITextField) {
        checkName()
        navigationItem.title = textField.text
    }
    
    func textFieldDidBeginEditing(_ textField: UITextField) {
        saveButton.isEnabled = true
    }
    
    @IBAction func timeChanged(_ sender: Any)
    {
        checkDate()
    }
    
    @IBAction func cancel(_ sender: Any)
    {
        dismiss(animated: true, completion: nil)
    }
    
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?)
    {
        if saveButton === sender as! UIBarButtonItem
        {
            let name = groceryTextField.text
            var time = timePicker.date
            
            let timeInterval = floor(time.timeIntervalSinceReferenceDate/60) * 60
            
            time = NSDate(timeIntervalSinceReferenceDate: timeInterval) as Date
            
            
            let notification = UILocalNotification()
            notification.alertTitle = "Reminder"
            notification.alertBody = "Don't forget that your \(name!) expires today!"
            notification.fireDate = time
            notification.soundName = UILocalNotificationDefaultSoundName
            
            UIApplication.shared.scheduleLocalNotification(notification)
            
            grocery = Grocery(name: name!, time: time as NSDate, notification: notification)
            
        }
    }

}
