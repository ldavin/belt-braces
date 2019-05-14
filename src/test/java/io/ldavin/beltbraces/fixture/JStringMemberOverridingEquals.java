package io.ldavin.beltbraces.fixture;

import java.util.Objects;

public class JStringMemberOverridingEquals {

    private String venus;

    public JStringMemberOverridingEquals(String venus) {
        this.venus = venus;
    }

    public String getVenus() {
        return venus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JStringMemberOverridingEquals that = (JStringMemberOverridingEquals) o;
        return Objects.equals(venus, that.venus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(venus);
    }
}
