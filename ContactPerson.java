import java.util.Scanner;

// Class representing a single contact
class Contact {
    String name; // Contact's name
    String phone; // Contact's phone number
    Contact left, right; // Pointers to left and right children in the tree

    // Constructor to initialize a contact
    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
        this.left = this.right = null;
    }
}

// Class representing the binary search tree for managing contacts
class ContactTree {
    private Contact root; // Root of the tree

    // Method to add a new contact
    public void addContact(String name, String phone) {
        root = addContactRecursive(root, name, phone);
    }

    // Helper method to recursively add a contact to the tree
    private Contact addContactRecursive(Contact node, String name, String phone) {
        // If the current node is null, create a new contact
        if (node == null) {
            return new Contact(name, phone);
        }

        // Compare the name and decide whether to go left or right
        if (name.compareTo(node.name) < 0) {
            node.left = addContactRecursive(node.left, name, phone);
        } else if (name.compareTo(node.name) > 0) {
            node.right = addContactRecursive(node.right, name, phone);
        }

        return node; // Return the updated node
    }

    // Method to search for a contact by name
    public void searchContact(String name) {
        Contact result = searchRecursive(root, name);
        if (result == null) {
            System.out.println("Contact not found.");
        } else {
            System.out.println("Contact Found: " + result.name + " - " + result.phone);
        }
    }

    // Helper method to recursively search for a contact
    private Contact searchRecursive(Contact node, String name) {
        // Base case: node is null or name matches the current node's name
        if (node == null || node.name.equals(name)) {
            return node;
        }

        // Recursively search in the left or right subtree based on comparison
        return name.compareTo(node.name) < 0
                ? searchRecursive(node.left, name)
                : searchRecursive(node.right, name);
    }

    // Method to delete a contact by name
    public void deleteContact(String name) {
        root = deleteContactRecursive(root, name);
    }

    // Helper method to recursively delete a contact
    private Contact deleteContactRecursive(Contact node, String name) {
        // Base case: node is null, meaning the contact was not found
        if (node == null) {
            System.out.println("Contact not found.");
            return null;
        }

        // Navigate to the correct node based on name comparison
        if (name.compareTo(node.name) < 0) {
            node.left = deleteContactRecursive(node.left, name);
        } else if (name.compareTo(node.name) > 0) {
            node.right = deleteContactRecursive(node.right, name);
        } else {
            // Node to delete found

            // Case 1: Node has no children
            if (node.left == null && node.right == null) {
                return null;
            }

            // Case 2: Node has one child
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }

            // Case 3: Node has two children
            // Find the smallest contact in the right subtree
            Contact smallest = findSmallest(node.right);
            // Replace current node's data with the smallest contact's data
            node.name = smallest.name;
            node.phone = smallest.phone;
            // Delete the smallest contact from the right subtree
            node.right = deleteContactRecursive(node.right, smallest.name);
        }
        return node; // Return the updated node
    }

    // Helper method to find the smallest contact in a subtree
    private Contact findSmallest(Contact node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // Method to display all contacts in alphabetical order
    public void displayContacts() {
        if (root == null) {
            System.out.println("No contacts available.");
        } else {
            System.out.println("Contacts List:");
            inOrderTraversal(root);
        }
    }

    // Helper method for in-order traversal of the tree
    private void inOrderTraversal(Contact node) {
        if (node != null) {
            inOrderTraversal(node.left); // Traverse the left subtree
            System.out.println(node.name + " - " + node.phone); // Visit the current node
            inOrderTraversal(node.right); // Traverse the right subtree
        }
    }
}

// Main class for the contact manager application
public class ContactPerson {
    public static void main(String[] args) {
        ContactTree contactTree = new ContactTree();
        Scanner scanner = new Scanner(System.in);

        // Interactive menu for managing contacts
        while (true) {
            System.out.println("\nContact Person");
            System.out.println("1. Add Contact");
            System.out.println("2. Search Contact");
            System.out.println("3. Display All Contacts");
            System.out.println("4. Delete Contact");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Add a new contact
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Phone: ");
                    String phone = scanner.nextLine();
                    contactTree.addContact(name, phone);
                    System.out.println("Contact added successfully.");
                    break;

                case 2:
                    // Search for a contact
                    System.out.print("Enter Name to Search: ");
                    String searchName = scanner.nextLine();
                    contactTree.searchContact(searchName);
                    break;

                case 3:
                    // Display all contacts
                    contactTree.displayContacts();
                    break;

                case 4:
                    // Delete a contact
                    System.out.print("Enter Name to Delete: ");
                    String deleteName = scanner.nextLine();
                    contactTree.deleteContact(deleteName);
                    break;

                case 5:
                    // Exit the program
                    System.out.println("Exiting program...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
