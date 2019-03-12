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
    
    @IBOutlet weak var segment: UISegmentedControl!
    
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
            
            
            let calendar = Calendar.current
            
            let fiveBefore = calendar.date(byAdding: .day, value: -5, to: time)
            let threeBefore = calendar.date(byAdding: .day, value: -3, to: time)
            let oneBefore = calendar.date(byAdding: .day, value: -1, to: time)
            
            
         //   print(timePicker.date.description(with: .current))
            
            let notification = UILocalNotification()
//            notification.alertTitle = "Reminder"
//            notification.alertBody = "Don't forget that your \(name!) expires today!"
//            notification.fireDate = time
            
            notification.soundName = UILocalNotificationDefaultSoundName
            
            switch(segment.selectedSegmentIndex)
            {
            case 0:
                notification.alertTitle = "Reminder"
                notification.alertBody = "Don't forget that your \(name!) expires tomorrow!"
                notification.fireDate = oneBefore
                break;
            case 1:
                notification.alertTitle = "Reminder"
                notification.alertBody = "Don't forget that your \(name!) expires in 3 days!"
                notification.fireDate = threeBefore
                break;
            case 2:
                notification.alertTitle = "Reminder"
                notification.alertBody = "Don't forget that your \(name!) expires in 5 days!"
                notification.fireDate = fiveBefore
            default:
                notification.alertTitle = "Reminder"
                notification.alertBody = "Don't forget that your \(name!) expires today!"
                notification.fireDate = time
            }
            
            UIApplication.shared.scheduleLocalNotification(notification)
            
            grocery = Grocery(name: name!, time: time as NSDate, notification: notification)
            
        }
    }

}
