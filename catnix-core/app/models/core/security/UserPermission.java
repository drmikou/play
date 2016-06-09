package models.core.security;

import be.objectify.deadbolt.core.models.Permission;

import javax.persistence.*;

import com.avaje.ebean.Model;

/**
 * UserPermissionTable register special authorisation for users. 
 */
@Entity
public class UserPermission extends Model implements Permission
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public int id;

    @Column(nullable = false,
            unique = true)
    public String value;

    public UserPermission()
    {
        // no-op
    }

    private UserPermission(Builder builder)
    {
        value = builder.value;
    }

    @Override
    public String getValue()
    {
        return null;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        if (!super.equals(o))
        {
            return false;
        }

        UserPermission that = (UserPermission) o;

        return !(value != null ? !value.equals(that.value) : that.value != null);

    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    public static final class Builder
    {
        private String value;

        public Builder value(String value)
        {
            this.value = value;
            return this;
        }

        public UserPermission build()
        {
            return new UserPermission(this);
        }
    }
}
